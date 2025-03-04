package br.com.voting_system_user_service.service;


import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.voting_system_user_service.repository.UserRepository;


import br.com.voting_system_user_service.dto.*;
import br.com.voting_system_user_service.entity.User;
import br.com.voting_system_user_service.enums.Role;
import br.com.voting_system_user_service.security. JwtUtil;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER); 
        userRepository.save(user);

        return "Usuário cadastrado com sucesso!";
    }

    public String loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return jwtUtil.generateToken(user.getId(), user.getRole());
    }
}