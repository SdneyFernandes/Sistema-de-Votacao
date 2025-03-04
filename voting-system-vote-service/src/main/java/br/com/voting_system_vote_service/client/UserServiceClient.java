package br.com.voting_system_vote_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import br.com.voting_system_vote_service.dto.UserResponse;

/**
 * @author fsdney
 */

@FeignClient(name = "user-service", url = "http://localhost:8081/api/users")
public interface UserServiceClient {
    
    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);
}

