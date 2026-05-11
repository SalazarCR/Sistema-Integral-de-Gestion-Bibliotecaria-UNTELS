package pe.edu.untels.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameUser(String usernameUser);
    Optional<User> findByEmailUser(String emailUser);
    Optional<User> findByDniUser(String dniUser);

    Boolean existsByUsernameUser(String usernameUser);
    Boolean existsByEmailUser(String emailUser);
    Boolean existsByDniUser(String dniUser);

    Page<User> findByRole_NameRole(String nameRole, Pageable pageable);
    Page<User> findByStatusUser(Boolean statusUser, Pageable pageable);
    Page<User> findByRole_NameRoleAndStatusUser(String nameRole, Boolean statusUser, Pageable pageable);
}
