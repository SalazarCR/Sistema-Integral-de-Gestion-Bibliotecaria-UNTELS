package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.dtos.ApiResponseDTO;
import pe.edu.untels.dtos.UserDTO;

public interface IUserService {

    ApiResponseDTO registrarUsuario(UserDTO userDTO);

    ApiResponseDTO listarUsuarios(int page, int size);

    ApiResponseDTO actualizarUsuario(Integer id, UserDTO userDTO);

    ApiResponseDTO toggleEstadoUsuario(Integer id);

    ApiResponseDTO buscarPorUsername(String username);

    ApiResponseDTO buscarPorDni(String dni);

    ApiResponseDTO filtrarUsuarios(String rol, Boolean estado, int page, int size);
}
