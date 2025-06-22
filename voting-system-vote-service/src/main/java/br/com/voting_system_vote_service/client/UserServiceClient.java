package br.com.voting_system_vote_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


import br.com.voting_system_user_service.dto. *;

/**
 * @author fsdney
 */

@FeignClient(name = "voting-system-user-service", path = "/api/users")
public interface UserServiceClient {

    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}


