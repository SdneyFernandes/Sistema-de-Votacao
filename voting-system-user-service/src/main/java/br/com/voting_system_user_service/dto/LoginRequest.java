package br.com.voting_system_user_service.dto;

import jakarta.validation.constraints. *;
import lombok.Data;

/**
 * @author fsdney
 */

//Dados de entrada para login

@Data
public class LoginRequest {
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
}
 
