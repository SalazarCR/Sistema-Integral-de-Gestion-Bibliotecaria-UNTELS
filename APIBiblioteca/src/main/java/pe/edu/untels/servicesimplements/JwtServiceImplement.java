package pe.edu.untels.servicesimplements;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.User;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtServiceImplement {

    @Value("${security.jwt.secret:your-secret-key-here-min-32-characters}")
    private String jwtSecret;

    @Value("${security.jwt.expiration:86400000}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh-expiration:604800000}")
    private long refreshExpiration;

    public String generarToken(User user) {
        SecretKey key = getSigningKey();
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(jwtExpiration);

        return Jwts.builder()
                .subject(user.getUsernameUser())
                .claim("userId", user.getIdUser())
                .claim("role", user.getRole().getNameRole())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(key)
                .compact();
    }

    public String generarRefreshToken(User user) {
        SecretKey key = getSigningKey();
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(refreshExpiration);

        return Jwts.builder()
                .subject(user.getUsernameUser())
                .claim("userId", user.getIdUser())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
