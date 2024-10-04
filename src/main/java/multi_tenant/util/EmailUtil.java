package multi_tenant.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import multi_tenant.auth.entity.UserClient;

@Component
public class EmailUtil {

    private final JavaMailSender mailSender;

    private static final String LINK = "https://github.com/raphaelfnds";
    private static final String LINK_SUPPORT = "https://www.google.com/";

    @Autowired
    public EmailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(body, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom("naoresponda@gmail.com.br");//aqui incluir email real

            mailSender.send(mimeMessage);

            System.out.println("E-mail enviado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateSignature() {
        return "<br/><br/>Atenciosamente,<br/>Equipe <a href=\"" + LINK
                + "\"><strong>Raphael Fernandes</strong></a><br/><br/>";
    }

    public String generateRecoveryEmailBody(UserClient userClient, String recoveryLink) {
        return "<p>Olá " + userClient.getNameUser() + ",</p>"
                + "<p>Recebemos uma solicitação para redefinir sua senha no sistema.</p>"
                + "<p>Por favor, <a href=\""+ recoveryLink + "\">clique aqui</a> para redefinir sua senha.</p>"
                + "<p>Se você não solicitou a redefinição de senha, por favor, entre em contato com o <a href=\"" + LINK_SUPPORT + "\">suporte</a> imediatamente.</p>" 
                + generateSignature();
    }

    public void sendPasswordRecoveryEmail(UserClient userClient, String appUrl, String token) {
        String recoveryLink = appUrl + "/reset-password?token=" + token;
        String subject = "Redefinição de Senha";
        String body = generateRecoveryEmailBody(userClient, recoveryLink);

        sendEmail(userClient.getEmail(), subject, body);
    }
}

