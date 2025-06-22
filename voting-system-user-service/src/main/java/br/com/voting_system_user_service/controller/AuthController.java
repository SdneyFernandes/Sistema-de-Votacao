package br.com.voting_system_user_service.controller;

import br.com.voting_system_user_service.service.AuthService;
import br.com.voting_system_user_service.dto.*;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * @author fsdney
 */

@Tag(name = "Autenticação", description = "Endpoints públicos para login e registro")
@RestController
@RequestMapping("/api/users")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController (AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Registrar", description = "Método para registrar usuário")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        logger.info("Recebida requisição para registrar novo usuário");
        String message = authService.registerUser(request);
        return ResponseEntity.ok(new AuthResponse(message, null));
    }

    @Operation(summary = "Login", description = "Método para logar usuário")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        logger.info("Recebida requisição para login de usuário");
        String token = authService.loginUser(request);
        return ResponseEntity.ok(new AuthResponse("Login realizado com sucesso", token));
    }
}