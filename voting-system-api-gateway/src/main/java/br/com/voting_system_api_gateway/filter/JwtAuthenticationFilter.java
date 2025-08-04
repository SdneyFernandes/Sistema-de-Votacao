package br.com.voting_system_api_gateway.filter;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.server.reactive.ServerHttpRequest; // Importante!

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/users/login",
            "/api/users/register",
            "/swagger-ui",
            "/v3/api-docs"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        HttpMethod method = exchange.getRequest().getMethod();

        if (HttpMethod.OPTIONS.equals(method)) {
            logger.debug("Requisição OPTIONS liberada para: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return exchange.getResponse().setComplete();
        }

        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            logger.debug("Rota pública acessada: {}", path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header ausente ou malformado em: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        logger.info("Token recebido no Gateway: {}", token);  // Log do token recebido

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.getSubject();
            String role = (String) claims.get("role");

            logger.info("Autenticado no gateway - ID: {}, Role: {}, Path: {}", userId, role, path);

            // Logs dos valores extraídos do token
            logger.info("Extraído do token - userId: {}, role: {}", userId, role);

            // Adaptação para versões mais antigas do Spring Cloud Gateway
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Role", role)
                    .build();

            // Log dos cabeçalhos que estão sendo adicionados
            logger.info("Adicionando cabeçalhos - X-User-Id: {}, X-User-Role: {}", userId, role);

            ServerWebExchange mutated = exchange.mutate()
                    .request(request) // Use o objeto ServerHttpRequest diretamente
                    .build();

            return chain.filter(mutated);

        } catch (JwtException e) {
            logger.warn("JWT inválido - Erro: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
