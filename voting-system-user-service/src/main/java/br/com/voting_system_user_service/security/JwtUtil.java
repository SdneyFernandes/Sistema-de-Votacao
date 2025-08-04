package br.com.voting_system_user_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import br.com.voting_system_user_service.enums.Role;

/**
 * @author fsdney

 */




/*Classe utilitária para gerar e validar tokens JWT.
Gera um token com um tempo de expiração definido e um segredo para assinatura.
Possui métodos para extrair o nome de usuário do token e validar a integridade do token.*/


@Component
public class JwtUtil {

	 @Value("${jwt.secret}")
	    private String secretKey; 
	 
	 	private static final long EXPIRATION_TIME = 86400000; // 1 dia

	    private Key getSigningKey() {
	        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	    }

	    public String generateToken(Long userId, Role role) {
	        return Jwts.builder()
	                .setSubject(userId.toString())
	                .claim("role", role.name()) 
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
	                .compact();
	    }
	    
	    public String getUserIdFromToken(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject(); 
	    }

	    public String getUserRoleFromToken(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .get("role", String.class);
	    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) 
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    
    public String extractUsername(String token) {
        return extractClaim(token).getSubject(); 
    }

    private Claims extractClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
   
    
}
    
 // Adicione este método na sua classe JwtUtil
    public String generateServiceToken() {
        return Jwts.builder()
                .setSubject("service-account") // Identificador do serviço
                .claim("role", Role.SERVICE.name()) // Role especial para serviços
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    } 
    
}