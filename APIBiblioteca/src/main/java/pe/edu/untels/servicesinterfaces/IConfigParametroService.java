package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.dtos.ConfigParametroRequest;
import pe.edu.untels.dtos.ConfigParametroResponse;

import java.util.List;

public interface IConfigParametroService {

    List<ConfigParametroResponse> obtenerTodos();

    ConfigParametroResponse obtenerPorNombre(String nombre);

    ConfigParametroResponse crear(ConfigParametroRequest request);

    ConfigParametroResponse actualizar(String nombre, ConfigParametroRequest request);

    void eliminar(String nombre);

    // Métodos helper para obtener valores específicos
    int obtenerDiasPrestamo();

    int obtenerLimitePrestamos();

    double obtenerMultaRetraso();
}

