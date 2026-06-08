package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.Usuario;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByRol(String rol);

    List<Usuario> findByEstado(String estado);

    boolean existsByUsername(String username);

    Usuario findByUsername(String username);
}
