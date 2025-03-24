package br.com.voting_system_user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

import br.com.voting_system_user_service.security.*;

/**
 * @author fsdney
 */


/*
Configura a segurança da aplicação.
Define quais endpoints são acessíveis sem autenticação (/api/users/register e /api/users/login) e quais requerem autenticação.
Desabilita a proteção CSRF que é comum em APIs RESTful.
Configura a política de gerenciamento de sessão para ser sem estado (stateless),
 o que é apropriado para APIs que utilizam tokens JWT.*/


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF para permitir requisições POST em APIs REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll() // Público (registro e login)
                        .requestMatchers("/h2-console/**").permitAll() // Permitir acesso ao console do H2
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permite requisições OPTIONS
                        
                        // Somente ADMIN pode gerenciar usuários
                        .requestMatchers("/api/users").hasRole("ADMIN") // Listar usuários
                        .requestMatchers("/api/users/{id}").hasRole("ADMIN") // Deletar usuários
                        
                        // Somente ADMIN pode criar e deletar votações
                        .requestMatchers("/api/votes_session/create").hasRole("ADMIN") 
                        .requestMatchers("/api/votes_session/{id}").hasRole("ADMIN") 
                        .requestMatchers("/api/votes_session/{id}/delete").hasRole("ADMIN") 

                        // Usuários comuns podem apenas listar, buscar e ver resultados de votações
                        .requestMatchers("/api/votes_session").authenticated()
                        .requestMatchers("/api/votes_session/{id}").authenticated()
                        .requestMatchers("/api/votes_session/{id}/results").authenticated()

                        // Votação: qualquer usuário autenticado pode votar
                        .requestMatchers("/api/votes/{voteSessionId}/cast").authenticated()
                        
                        .requestMatchers("/api/users/me").authenticated()


                        // Qualquer outra requisição precisa estar autenticada
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // Permite uso do H2 Console
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/*Integração das Classes
Fluxo de Registro:

O cliente faz uma requisição POST para /api/users/register com os dados do RegisterRequest.
O AuthController chama o AuthService para processar o registro.
O AuthService verifica se o e-mail já está cadastrado, codifica a senha e salva o usuário no User Repository.
O AuthService retorna uma mensagem de sucesso.
Fluxo de Login:

O cliente faz uma requisição POST para /api/users/login com os dados do LoginRequest.
O AuthController chama o AuthService para processar o login.
O AuthService valida as credenciais e, se forem válidas, gera um token JWT usando JwtUtil.
O token é retornado ao cliente.
Segurança:

O SecurityConfig garante que apenas os endpoints de registro e login sejam acessíveis sem autenticação, enquanto outros endpoints requerem um token JWT válido.
Validação de Token:

O JwtUtil é utilizado para validar tokens em requisições subsequentes, garantindo que apenas usuários autenticados possam acessar recursos protegidos.*/