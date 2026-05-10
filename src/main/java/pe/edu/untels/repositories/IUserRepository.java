package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.User;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameUser(String usernameUser);
    Optional<User> findByEmailUser(String emailUser);
    boolean existsByUsernameUser(String usernameUser);
    boolean existsByEmailUser(String emailUser);
}

