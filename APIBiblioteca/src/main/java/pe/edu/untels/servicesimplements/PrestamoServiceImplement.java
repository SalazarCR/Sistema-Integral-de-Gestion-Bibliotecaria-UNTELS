package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.Prestamo;
import pe.edu.untels.repositories.IPrestamoRepository;
import pe.edu.untels.servicesinterfaces.IPrestamoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServiceImplement implements IPrestamoService {

    @Autowired
    private IPrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> list() {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo insert(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public Optional<Prestamo> listId(int id) {
        return prestamoRepository.findById(id);
    }

    @Override
    public void edit(Prestamo prestamo) {
        prestamoRepository.save(prestamo);
    }

    @Override
    public void delete(int id) {
        prestamoRepository.deleteById(id);
    }

    @Override
    public List<Prestamo> buscarPorEstado(String estado) {
        return prestamoRepository.findByEstado(estado);
    }

    @Override
    public List<Prestamo> buscarPorEstudiante(int idEstudiante) {
        return prestamoRepository.findByEstudianteIdUsuario(idEstudiante);
    }

    @Override
    public List<Prestamo> buscarPorLibro(int idLibro) {
        return prestamoRepository.findByLibroIdLibro(idLibro);
    }

    @Override
    public List<Prestamo> buscarVigentesVencidos(LocalDateTime fecha) {
        return prestamoRepository.findByEstadoAndFechaEntregaBefore("vigente", fecha);
    }
}
