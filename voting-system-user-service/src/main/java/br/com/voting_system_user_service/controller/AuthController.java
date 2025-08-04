package br.com.voting_system_user_service.controller;

import br.com.voting_system_user_service.service.AuthService;
import br.com.voting_system_user_service.dto.*;
import br.com.voting_system_user_service.security.JwtUtil;

import org.springframework.http.MediaType;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;



/**
 * @author fsdney
 */

@Tag(name = "Autenticação", description = "Endpoints públicos para login e registro")
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController (AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        logger.info("Recebida requisição para registrar novo usuário");
        try {
            String message = authService.registerUser(request);
            return ResponseEntity.ok(new AuthResponse(message, null));
        } catch (RuntimeException ex) {
            logger.error("Erro ao registrar usuário: {}", ex.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new AuthResponse(ex.getMessage(), null));
        } catch (Exception ex) {
            logger.error("Erro interno inesperado: {}", ex.getMessage());
            return ResponseEntity
                    .status(500)
                    .body(new AuthResponse("Erro interno ao processar registro", null));
        }
    }



    @Operation(summary = "Login", description = "Método para logar usuário")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        logger.info("Recebida requisição para login de usuário");
        String token = authService.loginUser(request);
        return ResponseEntity.ok(new AuthResponse("Login realizado com sucesso", token));
    }
    
    @PostMapping("/api/auth/service-token")
    public ResponseEntity<String> generateServiceToken() {
        String token = jwtUtil.generateServiceToken();
        return ResponseEntity.ok(token);
    }
    
} 