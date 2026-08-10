package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.untels.entities.Carrera;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
}
