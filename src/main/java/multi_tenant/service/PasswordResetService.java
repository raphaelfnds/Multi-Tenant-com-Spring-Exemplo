package multi_tenant.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import multi_tenant.entity.PasswordResetToken;
import multi_tenant.entity.UserClient;
import multi_tenant.repository.PasswordResetTokenRepository;
import multi_tenant.repository.UserClientRepository;
import multi_tenant.util.EmailUtil;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserClientRepository userClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailUtil emailUtil;

    @Autowired
    public PasswordResetService(PasswordResetTokenRepository tokenRepository, UserClientRepository userClientRepository, PasswordEncoder passwordEncoder, EmailUtil emailUtil) {
        this.tokenRepository = tokenRepository;
        this.userClientRepository = userClientRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailUtil = emailUtil;
    }
    
    public void generatePasswordResetToken(String email) {
        UserClient userClient = userClientRepository.findUserAcessByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado ou inativo para o email: " + email));

        PasswordResetToken token;

        Optional<PasswordResetToken> existingToken = tokenRepository.findByUserClientId(userClient.getId());

        if (existingToken.isPresent()) {
            token = existingToken.get();
            if (token.getExpiryDate().isAfter(LocalDateTime.now())) {
                throw new IllegalArgumentException("Existe uma solicitação ativa e enviada para seu email, por favor aguarde 10 minutos antes de uma nova solicitação.");
            } else {
                token.setToken(UUID.randomUUID().toString());
                token.setExpiryDate(LocalDateTime.now().plusMinutes(10));
                tokenRepository.save(token);
            }
        } else {
            String newToken = UUID.randomUUID().toString();
            token = new PasswordResetToken(newToken, userClient, LocalDateTime.now().plusMinutes(10));
            tokenRepository.save(token);
        }

        String appUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        emailUtil.sendPasswordRecoveryEmail(userClient, appUrl, token.getToken());
    }
    
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Token inválido ou expirado, por favor realize uma nova solicitação."));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            throw new IllegalArgumentException("Token expirado, por favor realize uma nova solicitação.");
        }

        UserClient userClient = resetToken.getUserClient();
        userClient.setPassword(passwordEncoder.encode(newPassword));
        userClientRepository.save(userClient);

        tokenRepository.delete(resetToken);
    }

}
