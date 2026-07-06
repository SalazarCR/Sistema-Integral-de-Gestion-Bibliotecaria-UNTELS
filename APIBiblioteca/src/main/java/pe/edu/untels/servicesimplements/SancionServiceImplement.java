package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.Sancion;
import pe.edu.untels.repositories.ISancionRepository;
import pe.edu.untels.servicesinterfaces.ISancionService;

import java.util.List;
import java.util.Optional;

@Service
public class SancionServiceImplement implements ISancionService {

    @Autowired
    private ISancionRepository sancionRepository;

    @Override
    public List<Sancion> list() {
        return sancionRepository.findAll();
    }

    @Override
    public Sancion insert(Sancion sancion) {
        return sancionRepository.save(sancion);
    }

    @Override
    public Optional<Sancion> listId(int id) {
        return sancionRepository.findById(id);
    }

    @Override
    public void edit(Sancion sancion) {
        sancionRepository.save(sancion);
    }

    @Override
    public void delete(int id) {
        sancionRepository.deleteById(id);
    }

    @Override
    public List<Sancion> buscarPorEstado(String estado) {
        return sancionRepository.findByEstado(estado);
    }

    @Override
    public List<Sancion> buscarPorEstudiante(int idEstudiante) {
        return sancionRepository.findByEstudianteIdUsuario(idEstudiante);
    }
}
