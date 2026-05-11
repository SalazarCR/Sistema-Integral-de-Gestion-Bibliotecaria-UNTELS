package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import pe.edu.untels.entities.User;
import java.security.Key;
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
        return Jwts.builder()
                .setSubject(user.getUsernameUser())
                .claim("userId", user.getIdUser())
                .claim("role", user.getRole().getNameRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generarRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsernameUser())
                .claim("userId", user.getIdUser())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

