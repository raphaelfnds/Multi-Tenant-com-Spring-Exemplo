package multi_tenant.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import multi_tenant.auth.entity.PasswordResetToken;
import multi_tenant.auth.entity.UserClient;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	
    Optional<PasswordResetToken> findByToken(String token);
    
    void deleteByUserClient(UserClient userClient);
    
    Optional<PasswordResetToken> findByUserClientId(Long userId);
}
