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
                		.requestMatchers(
                			    "/api/users/register", 
                			    "/api/users/login",
                			    "/api/auth/service-token", 
                			    "/swagger-ui/**",
                			    "/v3/api-docs/**",            
                			    "/swagger-resources/**",      
                			    "/webjars/**",               
                			    "/h2-console/**",
                			    "/api/users/{id}"
                			).permitAll()
                	    
                	    // Regras para rotas protegidas abaixo
                	    .requestMatchers("/api/users").hasRole("ADMIN")
                	    //.requestMatchers("/api/users/{id}").hasAnyRole("ADMIN", "SERVICE")
                	    .requestMatchers("/api/internal/**").permitAll()
                	    .requestMatchers("/api/users/{userName}").hasRole("ADMIN")
                	    .requestMatchers("/api/votes_session/create").hasRole("ADMIN")
                	    .requestMatchers("/api/votes_session/{id}").hasRole("ADMIN")
                	    .requestMatchers("/api/votes_session/{id}/delete").hasRole("ADMIN")
                	    .requestMatchers("/api/votes_session").authenticated()
                	    .requestMatchers("/api/votes_session/{id}").authenticated()
                	    .requestMatchers("/api/votes_session/{id}/results").authenticated()
                	    .requestMatchers("/api/votes/{voteSessionId}/cast").authenticated()
                	    .requestMatchers("/api/users/me").authenticated()

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