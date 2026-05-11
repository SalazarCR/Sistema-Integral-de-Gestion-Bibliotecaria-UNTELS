package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.TokenBlacklist;
import java.util.Optional;

@Repository
public interface ITokenBlacklistRepository extends JpaRepository<TokenBlacklist, Integer> {
    Optional<TokenBlacklist> findByTokenValue(String tokenValue);
}

