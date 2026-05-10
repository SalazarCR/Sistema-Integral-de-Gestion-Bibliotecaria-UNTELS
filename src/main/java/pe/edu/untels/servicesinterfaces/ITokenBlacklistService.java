package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.entities.TokenBlacklist;
import java.util.List;
import java.util.Optional;

public interface ITokenBlacklistService {
    TokenBlacklist addToBlacklist(int idUser, String reason);
    TokenBlacklist addToBlacklist(int idUser, String sessionToken, String reason);
    boolean isTokenBlacklisted(String sessionToken);
    boolean isUserLoggedOut(int idUser);
    List<TokenBlacklist> getBlacklistByUser(int idUser);
    Optional<TokenBlacklist> getBlacklist(String sessionToken);
    void cleanExpiredBlacklist();
}

