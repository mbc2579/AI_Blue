package com.blue.gateway.config;

import com.blue.gateway.infrastructure.AuthClient;
import com.blue.gateway.infrastructure.AuthRule;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.*;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter implements GlobalFilter{

    private AuthClient authClient;

    private PathMatcher pathMatcher = new AntPathMatcher();

    @Value("${service.jwt.secret-key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;

    private List<String> excludeUrls;

    //CUSTOMER가 접근할 수 없는 api
    private List<AuthRule> customerRules;

    //OWNER가 접근할 수 없는 api
    private List<AuthRule> ownerRules;



    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);

        excludeUrls = Arrays.asList("/api/auth/logIn", "/api/auth/signUp");

        customerRules = new ArrayList<>();
        customerRules.add(new AuthRule("/api/auth/authority", Set.of(HttpMethod.GET)));
        customerRules.add(new AuthRule("/api/stores/**", Set.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)));
        customerRules.add(new AuthRule("/api/products/**", Set.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)));
        customerRules.add(new AuthRule("/api/payments/**", Set.of(HttpMethod.GET)));


        ownerRules = new ArrayList<>();
        ownerRules.add(new AuthRule("/api/auth/authority", Set.of(HttpMethod.GET)));
        ownerRules.add(new AuthRule("/api/destination/**", Set.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.GET)));
        ownerRules.add(new AuthRule("/api/stores/{storeId}/reviews", Set.of(HttpMethod.POST)));
        ownerRules.add(new AuthRule("/api/stores/{storeId}/reviews/{reviewId}", Set.of(HttpMethod.PUT)));
        ownerRules.add(new AuthRule("/api/orders", Set.of(HttpMethod.POST)));
        ownerRules.add(new AuthRule("/api/payments", Set.of(HttpMethod.POST)));
        ownerRules.add(new AuthRule("/api/payments/{orderId}", Set.of(HttpMethod.DELETE)));

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("@@@@@@@@@@@@PATH : " + path);


        if (isExcludeUrl(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        log.info("@@@@@@@@@@@@Auth Header : " + authHeader);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            log.info("@@@@@@@@@@@@JWT Token : " + jwt);

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String userName = claims.getSubject();
                exchange.getRequest().mutate().header("X-User-Name", userName);

                HttpMethod httpMethod = exchange.getRequest().getMethod();
                String role = (authClient.getAuthority(userName)).getAuthority();

                if(hasAccess(path, httpMethod, role)){
                    return chain.filter(exchange);
                }



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
            if(excludeUrl.equals(path)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAccess(String endPoint, HttpMethod method, String role){
        if(role.equals("CUSTOMER")){
            for(AuthRule rule : customerRules){
                if(endPoint.matches(rule.getEndpointPattern()) && rule.getDeniedMethods().contains(method)){
                    return false;
                }
            }
            return true;
        }else if(role.equals("OWNER")){
            for(AuthRule rule : ownerRules){
                if(endPoint.matches(rule.getEndpointPattern()) && rule.getDeniedMethods().contains(method)){
                    return false;
                }
            }
            return true;
        }else if(role.equals("MANAGER")||role.equals("MASTER")){
            return true;
        }

        return false;

    }

}
