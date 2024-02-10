package uz.cosinus.thinkstore.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.entity.UserEntity;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.access_expiry}")
    private Integer accessExpiry;

    @Value("${jwt.refresh_expiry}")
    private Integer refreshExpiry;

    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken (UserEntity user) {
        if(user.isEnabled()) {
            Date iat = new Date();

            return Jwts.builder()
                    .setSubject(user.getId().toString())
                    .setIssuedAt(iat)
                    .setExpiration(new Date(iat.getTime() + accessExpiry))
                    .addClaims(getAuthorities(user))
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .claim("roles", Collections.singleton(user.getRole().name()))
                    .compact();
        }
        throw new AuthenticationCredentialsNotFoundException("User is not active");
    }
    public String generateRefreshToken (UserEntity user) {
        if(user.isEnabled()) {
            Date iat = new Date();

            return Jwts.builder()
                    .setSubject(user.getId().toString())
                    .setIssuedAt(iat)
                    .setExpiration(new Date(iat.getTime() + refreshExpiry))
//                    .addClaims(getAuthorities(user))
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .compact();
        }
        throw new AuthenticationCredentialsNotFoundException("User is not active");
    }

    public Jws<Claims> extractToken (String token) {
        return  Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token);
    }

    private Map<String, Object> getAuthorities(UserEntity user) {
        return Map.of("roles",
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                );
    }
}
