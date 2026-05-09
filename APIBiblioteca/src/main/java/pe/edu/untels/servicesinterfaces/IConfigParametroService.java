package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.dtos.ConfigParametroDTO;

public interface IConfigParametroService {
    ConfigParametroDTO registrarLimitePrestamos(int limitePrestamos);
    ConfigParametroDTO obtenerConfiguracion();
}
