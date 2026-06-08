package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.entities.Notificacion;

import java.util.List;
import java.util.Optional;

public interface INotificacionService {

    List<Notificacion> list();

    Notificacion insert(Notificacion notificacion);

    Optional<Notificacion> listId(int id);

    void edit(Notificacion notificacion);

    void delete(int id);

    List<Notificacion> buscarPorEstudiante(int idEstudiante);

    List<Notificacion> buscarPendientesPorEstudiante(int idEstudiante);
}
