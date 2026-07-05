package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.Prestamo;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPrestamoRepository extends JpaRepository<Prestamo, Integer> {

    List<Prestamo> findByEstado(String estado);

    List<Prestamo> findByEstudianteIdUsuario(int idEstudiante);

    List<Prestamo> findByLibroIdLibro(int idLibro);

    List<Prestamo> findByEstadoAndFechaEntregaBefore(String estado, LocalDateTime fecha);
}
