package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.ConfiguracionBiblioteca;
import pe.edu.untels.repositories.IConfiguracionBibliotecaRepository;
import pe.edu.untels.servicesinterfaces.IConfiguracionBibliotecaService;

import java.util.Optional;

@Service
public class ConfiguracionBibliotecaServiceImplement implements IConfiguracionBibliotecaService {

    @Autowired
    private IConfiguracionBibliotecaRepository configuracionRepository;

    @Override
    public Optional<ConfiguracionBiblioteca> obtener() {
        return configuracionRepository.findById(1);
    }

    @Override
    public void actualizar(ConfiguracionBiblioteca configuracion) {
        configuracionRepository.save(configuracion);
    }
}
