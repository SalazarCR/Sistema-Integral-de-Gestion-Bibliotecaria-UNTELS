package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.Notificacion;

import java.util.List;

@Repository
public interface INotificacionRepository extends JpaRepository<Notificacion, Integer> {

    List<Notificacion> findByEstudianteIdUsuario(int idEstudiante);

    List<Notificacion> findByEstudianteIdUsuarioAndLeida(int idEstudiante, boolean leida);
}
