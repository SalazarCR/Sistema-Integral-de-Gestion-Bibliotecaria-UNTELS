package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.dtos.ConfigParametroDTO;
import pe.edu.untels.entities.ConfigParametro;
import pe.edu.untels.repositories.IConfigParametroRepository;
import pe.edu.untels.servicesinterfaces.IConfigParametroService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConfigParametroServiceImplement implements IConfigParametroService {

    @Autowired
    private IConfigParametroRepository configRepo;

    @Override
    public ConfigParametroDTO registrarLimitePrestamos(int limitePrestamos) {
        ConfigParametro config = obtenerOCrearConfig();
        config.setLimitePrestamos(limitePrestamos);
        config.setFechaActualizacion(LocalDateTime.now());
        return mapToDTO(configRepo.save(config));
    }

    @Override
    public ConfigParametroDTO obtenerConfiguracion() {
        return mapToDTO(obtenerOCrearConfig());
    }

    private ConfigParametro obtenerOCrearConfig() {
        List<ConfigParametro> configs = configRepo.findAll();
        if (configs.isEmpty()) {
            return new ConfigParametro(0, 0, 0);
        }
        return configs.get(0);
    }

    private ConfigParametroDTO mapToDTO(ConfigParametro c) {
        return new ConfigParametroDTO(
            c.getIdConfig(),
            c.getLimitePrestamos(),
            c.getDiasPrestamo(),
            c.getStockMinimo(),
            c.getFechaActualizacion()
        );
    }
}
