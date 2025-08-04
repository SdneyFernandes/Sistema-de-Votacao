package br.com.voting_system_vote_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import br.com.voting_system_vote_service.service.UserServiceClient;
import br.com.voting_system_vote_service.dto.UserResponse;
import org.springframework.stereotype.Service;



/**
 * @author fsdney
 */

@Service
public class ServiceAuthClient {
    
    private final UserServiceClient userServiceClient;
    
    @Value("${service.account.token}")
    private String serviceAccountToken;
    
    public ServiceAuthClient(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }
    
    // Método adicionado para fornecer o token
    public String getServiceToken() {
        return "Bearer " + serviceAccountToken;
    }
    
    public UserResponse getUserById(Long userId) {
        return userServiceClient.getUserById(
            userId, 
            getServiceToken() // Reutiliza o novo método
        );
    }
}