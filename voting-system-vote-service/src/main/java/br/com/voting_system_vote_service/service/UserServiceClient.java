package br.com.voting_system_vote_service.service;

import br.com.voting_system_vote_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author fsdney
 */



@FeignClient(name = "user-service-client", url = "${user.service.url}")
public interface UserServiceClient {

    @GetMapping("/api/users/{id}")
    UserResponse getUserById(
        @PathVariable Long id,
        @RequestHeader("Authorization") String token
    );
}