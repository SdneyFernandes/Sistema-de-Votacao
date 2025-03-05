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
	
	@Autowired
    private MeterRegistry meterRegistry;
	
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtUtil jwtUtil;

   

    public String registerUser(RegisterRequest request) {
    	
    	 long startTime = System.currentTimeMillis();
	        meterRegistry.counter("registro.usuario.chamadas").increment();

	        logger.info("Registrando usuario");
		
    	
        if (userRepository.existsByEmail(request.getEmail())) {
        	logger.warn("Já existe um usuario cadastrado com esse email");
            throw new RuntimeException("E-mail já cadastrado.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER); 
        userRepository.save(user);
        
        
        long duration = System.currentTimeMillis() - startTime;
        meterRegistry.timer("registro.usuario.chamadas.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

        logger.info("Usuario registrado com sucesso");
         
     
        return "Usuário registrado com sucesso!";
    }

    public String loginUser(LoginRequest request) {
    	
    	long startTime = System.currentTimeMillis();
        meterRegistry.counter("login.usuario.chamadas").increment();

        logger.info("Logando usuario");
    	
        User user = userRepository.findByEmail(request.getEmail())
    			.orElseThrow(() -> {
    				logger.warn("Email invalido");
    				return new RuntimeException("Credenciais inválidas");
    			});
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        	logger.warn("Password Invalida");
            throw new RuntimeException("Credenciais inválidas");
        }
        
        
        long duration = System.currentTimeMillis() - startTime;
        meterRegistry.timer("login.usuario.chamadas.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

        logger.info("Usuario Logado com sucesso");
        
        return jwtUtil.generateToken(user.getId(), user.getRole());
    }
}