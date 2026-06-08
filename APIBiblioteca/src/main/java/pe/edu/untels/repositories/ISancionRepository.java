package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.Sancion;

import java.util.List;

@Repository
public interface ISancionRepository extends JpaRepository<Sancion, Integer> {

    List<Sancion> findByEstado(String estado);

    List<Sancion> findByEstudianteIdUsuario(int idEstudiante);
}
