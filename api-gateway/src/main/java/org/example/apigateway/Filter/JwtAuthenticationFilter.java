package org.example.apigateway.Filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    private static final String SECRET_KEY = "MjgyNjI4NTgwNTczMDI1NTcyNTA0MTg3NjI3NTM4NzY=";

    private static final List<String> PROTECTED_PATHS = List.of(
            "/user/",
            "/restaurant/protected",
            "/shopping-cart"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        boolean requiresAuth = PROTECTED_PATHS.stream().anyMatch(path::startsWith);

        if  (requiresAuth) {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);
            try{
                Claims claims = Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                if (claims.getExpiration().before(new Date())){
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String username = claims.getSubject();
                String role = claims.get("role", String.class);
                Long id = claims.get("id", Long.class);

                exchange.getRequest().mutate()
                        .header("X-User-Username", username)
                        .header("X-User-Role", role)
                        .header("X-User-Id", String.valueOf(id))
                        .build();
            }
            catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
