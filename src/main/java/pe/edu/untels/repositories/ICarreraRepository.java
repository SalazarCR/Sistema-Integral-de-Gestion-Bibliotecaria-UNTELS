package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.Carrera;
import java.util.Optional;

@Repository
public interface ICarreraRepository extends JpaRepository<Carrera, Integer> {
    Optional<Carrera> findByNameCarrera(String nameCarrera);
    boolean existsByNameCarrera(String nameCarrera);
}

