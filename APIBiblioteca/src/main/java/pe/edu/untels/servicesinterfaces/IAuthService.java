package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;

public interface IAuthService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    Boolean validarRol(String rol);

    Boolean validarEstado(Integer userId);

    Boolean validarCredenciales(String username, String password);
}

