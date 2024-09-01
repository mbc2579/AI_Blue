package com.blue.gateway.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter implements GlobalFilter{

    private PathMatcher pathMatcher = new AntPathMatcher();

    @Value("${service.jwt.secret-key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;

    private List<String> excludeUrls;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);

        excludeUrls = Arrays.asList("/auth/logIn", "/auth/signUp");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();


        if (isExcludeUrl(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String userName = claims.getSubject();
                exchange.getRequest().mutate().header("X-User-Name", userName);

                return chain.filter(exchange);
            } catch (ExpiredJwtException ex) {
                log.error("JWT token has expired");
            } catch (UnsupportedJwtException ex) {
                log.error("Unsupported JWT token");
                return Mono.error(new JwtException("Unsupported JWT token", ex));
            } catch (MalformedJwtException ex) {
                log.error("Malformed JWT token");
                return Mono.error(new JwtException("Malformed JWT token", ex));
            } catch (SignatureException ex) {
                log.error("Invalid JWT signature");
                return Mono.error(new JwtException("Invalid JWT signature", ex));
            } catch (IllegalArgumentException ex) {
                log.error("Invalid JWT token");
                return Mono.error(new JwtException("Invalid JWT token", ex));
            }
        }

        log.error("Authorization header not found");
        return Mono.error(new Exception("Authorization header not found"));
    }

    private boolean isExcludeUrl(String path) {
        for(String excludeUrl : excludeUrls) {
            if(pathMatcher.isPattern(excludeUrl) && pathMatcher.match(excludeUrl, path)) {
                return true;
            }
        }
        return false;
    }

}
