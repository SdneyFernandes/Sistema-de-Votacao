package br.com.voting_system_user_service.controller;

import br.com.voting_system_user_service.service.AuthService;
import br.com.voting_system_user_service.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author fsdney
 */


/*Controlador que gerencia as requisições relacionadas à autenticação de usuários.
Possui dois endpoints: um para registro (/register) e outro para login (/login).
.*/


@RestController
@RequestMapping("/api/users")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthService authService;

	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
		logger.info("Recebida requisição para registrar novo usuario");
		return ResponseEntity.ok(authService.registerUser(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
		logger.info("Recebida requisição para logar usuario");
		return ResponseEntity.ok(authService.loginUser(request));
	}

}
