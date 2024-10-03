package multi_tenant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import multi_tenant.entity.UserClient;

@Repository
public interface UserClientRepository extends JpaRepository<UserClient, Long> {

	@Query("SELECT u FROM UserClient u WHERE u.email = :email AND u.situation = true")
	Optional<UserClient> findUserAcessByEmail(@Param("email") String email);
}
