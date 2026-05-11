package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
import pe.edu.untels.entities.User;
import pe.edu.untels.exceptions.AuthException;
import pe.edu.untels.exceptions.ValidationException;
import pe.edu.untels.repositories.IUserRepository;
import pe.edu.untels.servicesinterfaces.IAuthService;
import java.util.Optional;
/**
 * Realiza el proceso de login validando credenciales,
 * estado, rol y generando tokens JWT.
 */
@Service
public class AuthServiceImplement implements IAuthService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtServiceImplement jwtService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // Validar que username y password no sean nulos
        if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username es requerido");
        }
        if (loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password es requerido");
        }

        // Buscar usuario por username
        Optional<User> userOpt = userRepository.findByUsernameUser(loginRequestDTO.getUsername());

        if (!userOpt.isPresent()) {
            throw new AuthException("Usuario o contraseña inválidos");
        }

        User user = userOpt.get();

        // Validar contraseña (comparación simple para MVP)
        if (!validarCredenciales(user.getUsernameUser(), loginRequestDTO.getPassword())) {
            throw new AuthException("Usuario o contraseña inválidos");
        }

        // Validar que el usuario esté activo
        if (!validarEstado(user.getIdUser())) {
            throw new AuthException("Usuario inactivo o bloqueado");
        }

        // Validar rol
        if (!validarRol(user.getRole().getNameRole())) {
            throw new AuthException("Rol no válido");
        }

        // Generar tokens
        String token = jwtService.generarToken(user);
        String refreshToken = jwtService.generarRefreshToken(user);

        // Crear response
        LoginResponseDTO response = new LoginResponseDTO();
        response.setSuccess(true);
        response.setMessage("Login exitoso");
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        response.setRole(user.getRole().getNameRole());

        // Agregar información del usuario
        LoginResponseDTO.UserInfo userInfo = new LoginResponseDTO.UserInfo();
        userInfo.setId(user.getIdUser());
        userInfo.setUsername(user.getUsernameUser());
        userInfo.setEmail(user.getEmailUser());
        response.setUser(userInfo);

        return response;
    }

    @Override
    public Boolean validarRol(String rol) {
        // TT-AUTH-06: Validar que el rol sea válido
        if (rol == null || rol.trim().isEmpty()) {
            return false;
        }

        // Roles válidos: ADMIN, BIBLIOTECARIO, ESTUDIANTE
        return rol.equals("ADMIN") || rol.equals("BIBLIOTECARIO") || rol.equals("ESTUDIANTE");
    }

    @Override
    public Boolean validarEstado(Integer userId) {
        // TT-AUTH-09: Validar que el usuario esté activo
        Optional<User> userOpt = userRepository.findById(userId);

        if (!userOpt.isPresent()) {
            return false;
        }

        User user = userOpt.get();
        return user.getStatusUser() != null && user.getStatusUser();
    }

    @Override
    public Boolean validarCredenciales(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsernameUser(username);

        if (!userOpt.isPresent()) {
            return false;
        }

        User user = userOpt.get();

        // Comparación simple (Sin encriptación para MVP)
        // En producción usar BCryptPasswordEncoder
        return user.getPasswordUser().equals(password);
    }
}

