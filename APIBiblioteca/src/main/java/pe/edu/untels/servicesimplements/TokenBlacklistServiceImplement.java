package pe.edu.untels.servicesimplements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.TokenBlacklist;
import pe.edu.untels.repositories.ITokenBlacklistRepository;
import pe.edu.untels.servicesinterfaces.ITokenBlacklistService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TokenBlacklistServiceImplement implements ITokenBlacklistService {
    private static final Logger log = LoggerFactory.getLogger(TokenBlacklistServiceImplement.class);

    @Autowired
    private ITokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public TokenBlacklist addToBlacklist(int idUser, String reason) {
        return addToBlacklist(idUser, null, reason);
    }

    @Override
    public TokenBlacklist addToBlacklist(int idUser, String sessionToken, String reason) {
        TokenBlacklist tokenBlacklist = new TokenBlacklist(idUser, sessionToken, LocalDateTime.now(), reason);
        TokenBlacklist saved = tokenBlacklistRepository.save(tokenBlacklist);
        log.info("✅ Token/Sesión añadido a blacklist - Usuario ID: {} - Razón: {}", idUser, reason);
        return saved;
    }

    @Override
    public boolean isTokenBlacklisted(String sessionToken) {
        if (sessionToken == null || sessionToken.isBlank()) {
            return false;
        }
        boolean exists = tokenBlacklistRepository.existsBySessionToken(sessionToken);
        if (exists) {
            log.warn("⚠️ Token en blacklist detectado: {}", sessionToken);
        }
        return exists;
    }

    @Override
    public boolean isUserLoggedOut(int idUser) {
        List<TokenBlacklist> blacklist = tokenBlacklistRepository.findByIdUser(idUser);
        return !blacklist.isEmpty();
    }

    @Override
    public List<TokenBlacklist> getBlacklistByUser(int idUser) {
        return tokenBlacklistRepository.findByIdUser(idUser);
    }

    @Override
    public Optional<TokenBlacklist> getBlacklist(String sessionToken) {
        return tokenBlacklistRepository.findBySessionToken(sessionToken);
    }

    @Override
    public void cleanExpiredBlacklist() {
        // Limpiar tokens que tienen más de 24 horas
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        log.info("🧹 Limpiando tokens/sesiones expirados antes de: {}", twentyFourHoursAgo);
        // Implementar si es necesario con query personalizado
    }
}

