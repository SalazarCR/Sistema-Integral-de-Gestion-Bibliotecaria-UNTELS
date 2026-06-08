package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.Notificacion;
import pe.edu.untels.repositories.INotificacionRepository;
import pe.edu.untels.servicesinterfaces.INotificacionService;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServiceImplement implements INotificacionService {

    @Autowired
    private INotificacionRepository notificacionRepository;

    @Override
    public List<Notificacion> list() {
        return notificacionRepository.findAll();
    }

    @Override
    public Notificacion insert(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    public Optional<Notificacion> listId(int id) {
        return notificacionRepository.findById(id);
    }

    @Override
    public void edit(Notificacion notificacion) {
        notificacionRepository.save(notificacion);
    }

    @Override
    public void delete(int id) {
        notificacionRepository.deleteById(id);
    }

    @Override
    public List<Notificacion> buscarPorEstudiante(int idEstudiante) {
        return notificacionRepository.findByEstudianteIdUsuario(idEstudiante);
    }

    @Override
    public List<Notificacion> buscarPendientesPorEstudiante(int idEstudiante) {
        return notificacionRepository.findByEstudianteIdUsuarioAndLeida(idEstudiante, false);
    }
}
