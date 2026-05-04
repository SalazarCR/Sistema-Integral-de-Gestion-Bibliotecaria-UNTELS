package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.untels.entities.Bibliotecario;

import java.util.List;

public interface IBibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {

    List<Bibliotecario> findByActivoTrue();

}
