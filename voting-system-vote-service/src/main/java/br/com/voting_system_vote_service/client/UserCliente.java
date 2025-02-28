package br.com.voting_system_vote_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation. *;

import br.com.voting_system_user_service.client.UserResponse;


/**
 * @author fsdney
 */

@FeignClient(name = "voting-system-user-service", path = "/api/users")
public interface UserCliente {
	@GetMapping("/{id}")
	UserResponse getUserById(@PathVariable Long id);

}
