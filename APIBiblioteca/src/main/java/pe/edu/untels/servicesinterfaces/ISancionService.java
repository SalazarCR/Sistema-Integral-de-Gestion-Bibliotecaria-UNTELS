package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.entities.Sancion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ISancionService {

    List<Sancion> list();

    Sancion insert(Sancion sancion);

    Optional<Sancion> listId(int id);

    void edit(Sancion sancion);

    void delete(int id);

    List<Sancion> buscarPorEstado(String estado);

    List<Sancion> buscarPorEstudiante(int idEstudiante);

    List<Sancion> buscarActivasVencidas(LocalDateTime fecha);
}
