package br.com.voting_system_user_service.controller;

import br.com.voting_system_user_service.service.AuthService;
import br.com.voting_system_user_service.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author fsdney
 */


/*Controlador que gerencia as requisições relacionadas à autenticação de usuários.
Possui dois endpoints: um para registro (/register) e outro para login (/login).
.*/


@RestController
@RequestMapping("/api/users")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
		return ResponseEntity.ok(authService.registerUser(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
		return ResponseEntity.ok(authService.loginUser(request));
	}

}
