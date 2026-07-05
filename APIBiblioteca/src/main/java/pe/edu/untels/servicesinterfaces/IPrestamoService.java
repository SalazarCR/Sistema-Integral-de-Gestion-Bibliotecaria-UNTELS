package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.entities.Prestamo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IPrestamoService {

    List<Prestamo> list();

    Prestamo insert(Prestamo prestamo);

    Optional<Prestamo> listId(int id);

    void edit(Prestamo prestamo);

    void delete(int id);

    List<Prestamo> buscarPorEstado(String estado);

    List<Prestamo> buscarPorEstudiante(int idEstudiante);

    List<Prestamo> buscarPorLibro(int idLibro);

    List<Prestamo> buscarVigentesVencidos(LocalDateTime fecha);
}
