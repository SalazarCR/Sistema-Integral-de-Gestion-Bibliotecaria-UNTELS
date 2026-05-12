package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * Servicio de autenticación:
 * - Valida credenciales
 * - Verifica estado del usuario
 * - Valida roles
 * - Genera JWT y Refresh Token
 */
@Service
public class AuthServiceImplement implements IAuthService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtServiceImplement jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        // =========================
        // VALIDACIONES BÁSICAS
        // =========================
        if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username es requerido");
        }

        if (loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password es requerido");
        }

        // =========================
        // BUSCAR USUARIO
        // =========================
        User user = userRepository.findByUsernameUser(loginRequestDTO.getUsername())
                .orElseThrow(() -> new AuthException("Usuario o contraseña inválidos"));

        // =========================
        // VALIDAR PASSWORD (BCrypt)
        // =========================
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordUser())) {
            throw new AuthException("Usuario o contraseña inválidos");
        }

        // =========================
        // VALIDAR ESTADO
        // =========================
        if (!Boolean.TRUE.equals(user.getStatusUser())) {
            throw new AuthException("Usuario inactivo o bloqueado");
        }

        // =========================
        // VALIDAR ROL
        // =========================
        if (!validarRol(user.getRole().getNameRole())) {
            throw new AuthException("Rol no válido");
        }

        // =========================
        // GENERAR TOKENS
        // =========================
        String token = jwtService.generarToken(user);
        String refreshToken = jwtService.generarRefreshToken(user);

        // =========================
        // RESPONSE
        // =========================
        LoginResponseDTO response = new LoginResponseDTO();
        response.setSuccess(true);
        response.setMessage("Login exitoso");
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        response.setRole(user.getRole().getNameRole());

        LoginResponseDTO.UserInfo userInfo = new LoginResponseDTO.UserInfo();
        userInfo.setId(user.getIdUser());
        userInfo.setUsername(user.getUsernameUser());
        userInfo.setEmail(user.getEmailUser());

        response.setUser(userInfo);

        return response;
    }

    // =========================
    // VALIDAR ROL
    // =========================
    @Override
    public Boolean validarRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            return false;
        }

        return rol.equals("ADMIN")
                || rol.equals("BIBLIOTECARIO")
                || rol.equals("ESTUDIANTE");
    }

    // =========================
    // VALIDAR ESTADO
    // =========================
    @Override
    public Boolean validarEstado(Integer userId) {
        return userRepository.findById(userId)
                .map(user -> Boolean.TRUE.equals(user.getStatusUser()))
                .orElse(false);
    }

    // =========================
    // VALIDAR CREDENCIALES (REUTILIZABLE)
    // =========================
    @Override
    public Boolean validarCredenciales(String username, String password) {

        Optional<User> userOpt = userRepository.findByUsernameUser(username);

        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        return passwordEncoder.matches(password, user.getPasswordUser());
    }
}