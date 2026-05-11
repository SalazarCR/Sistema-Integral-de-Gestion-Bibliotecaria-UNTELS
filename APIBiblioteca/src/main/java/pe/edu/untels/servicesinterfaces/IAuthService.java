package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
/**
 * Interfaz del servicio de autenticación.
 * Maneja login, validación de rol, estado y credenciales.
 */
public interface IAuthService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    Boolean validarRol(String rol);

    Boolean validarEstado(Integer userId);

    Boolean validarCredenciales(String username, String password);
}

