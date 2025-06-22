package br.com.voting_system_user_service.service;


import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.voting_system_user_service.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import br.com.voting_system_user_service.dto.*;
import br.com.voting_system_user_service.entity.User;
import br.com.voting_system_user_service.enums.Role;
import br.com.voting_system_user_service.security. JwtUtil;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;


import java.util.Optional;

/**
 * @author fsdney
 */


/*Serviço que contém a lógica de negócios para autenticação e registro de usuários.
O método registerUser  verifica se o e-mail já está cadastrado, codifica a senha e salva o usuário no repositório.
O método loginUser  valida as credenciais do usuário e gera um token JWT se as credenciais forem válidas.*/


@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final MeterRegistry meterRegistry;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil ,PasswordEncoder passwordEncoder, UserRepository userRepository, MeterRegistry meterRegistry) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.meterRegistry = meterRegistry;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(RegisterRequest request) {
        long startTime = System.currentTimeMillis();
        meterRegistry.counter("usuario.registro.chamadas").increment();
        logger.info("Recebida requisição para registrar usuário com nome {}", request.getUserName());

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Tentativa de registro falhou, ja existe um usuario cadastrado com esse e-mail {}", request.getEmail());
            throw new RuntimeException("E-mail já cadastrado.");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);

        userRepository.save(user);

        long duration = System.currentTimeMillis() - startTime;
        meterRegistry.timer("usuario.registro.tempo").record(duration, TimeUnit.MILLISECONDS);
        logger.info("Usuário com nome {} registrado com sucesso", request.getUserName());

        return "Usuário registrado com sucesso!";
    }

    public String loginUser(LoginRequest request) {
        long startTime = System.currentTimeMillis();
        meterRegistry.counter("usuario.login.chamadas").increment();
        logger.info("Recebida requisição de login com e-mail: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.warn("Login falhou: e-mail não encontrado - {}", request.getEmail());
                    return new RuntimeException("Credenciais inválidas");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Login falhou: senha inválida para o e-mail {}", request.getEmail());
            throw new RuntimeException("Credenciais inválidas");
        }

        long duration = System.currentTimeMillis() - startTime;
        meterRegistry.timer("usuario.login.tempo").record(duration, TimeUnit.MILLISECONDS);
        logger.info("Usuário {} autenticado com sucesso", user.getUserName());

        return jwtUtil.generateToken(user.getId(), user.getRole());
    }
}