package br.com.voting_system_user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import br.com.voting_system_user_service.entity.User;


/**
 * @author fsdney
 */

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
}
