package org.example.restaurant_service.Config;

import io.jsonwebtoken.io.Decoders;
import jakarta.websocket.Decoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String JWT_SECRET = "MjgyNjI4NTgwNTczMDI1NTcyNTA0MTg3NjI3NTM4NzY=";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/restaurant/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder()).jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<GrantedAuthority> authorities = new ArrayList<>();

            // Add role from JWT
            String role = jwt.getClaimAsString("role");
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            // Add verified authority if restaurant is verified
            Boolean verified = jwt.getClaim("verified");
            if (Boolean.TRUE.equals(verified) && "ROLE_RESTAURANT".equals(role)) {
                authorities.add(new SimpleGrantedAuthority("VERIFIED_RESTAURANT"));
            }

            return authorities;
        });
        return converter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(jwtSecretKey()).build();
    }

    private SecretKey jwtSecretKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(JWT_SECRET);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}

