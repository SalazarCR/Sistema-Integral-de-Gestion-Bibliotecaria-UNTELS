package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.entities.ConfiguracionBiblioteca;

import java.util.Optional;

public interface IConfiguracionBibliotecaService {

    Optional<ConfiguracionBiblioteca> obtener();

    void actualizar(ConfiguracionBiblioteca configuracion);
}
