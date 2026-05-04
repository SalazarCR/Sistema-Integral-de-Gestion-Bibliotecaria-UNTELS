package pe.edu.untels.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
import pe.edu.untels.dtos.StatusResponseDTO;
import pe.edu.untels.dtos.ToggleResponseDTO;
import pe.edu.untels.entities.User;
import pe.edu.untels.servicesinterfaces.IUserService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IUserService userService;

    @Autowired(required = false)
    private AuthenticationProvider authProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        try {
            String identifier = loginRequestDTO.getUsernameUser() != null ? loginRequestDTO.getUsernameUser() : loginRequestDTO.getEmailUser();
            log.info(">>> [LOGIN] Intento de login — usuario: '{}' — desde IP: {}", identifier, request.getRemoteAddr());

            // Paso 1: Validar credenciales con el servicio
            LoginResponseDTO response = userService.login(loginRequestDTO);

            // Paso 2: Autenticar con Spring Security para crear contexto y sesión
            String username = identifier;
            String password = loginRequestDTO.getPasswordUser();

            try {
                // Crear token de autenticación
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);

                // Si existe el provider, usarlo para autenticar
                if (authProvider != null) {
                    Authentication authenticated = authProvider.authenticate(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authenticated);
                    log.info(">>> [LOGIN]  Contexto de Spring Security creado — usuario: '{}' — roles: {}",
                        username, authenticated.getAuthorities());
                } else {
                    // Fallback: crear sesión manual si no hay provider
                    request.getSession(true);
                    log.info(">>> [LOGIN]  Sesión HTTP creada (sin provider de autenticación)");
                }
            } catch (AuthenticationException ae) {
                log.warn(">>> [LOGIN]  Spring Security no pudo autenticar, pero credenciales son válidas: {}", ae.getMessage());
                // Continuar de todas formas si el servicio validó las credenciales
                request.getSession(true);
            }

            log.info(">>> [LOGIN]  LOGIN EXITOSO — usuario: '{}' — rol: {}", identifier, response.getRole());

            return ResponseEntity.ok(createResponse(true, "Login exitoso", response, 200));
        } catch (RuntimeException e) {
            log.warn(">>> [LOGIN]  Error en login: {}", e.getMessage());
            return ResponseEntity.status(401).body(createErrorResponse("Login fallido", e.getMessage(), 401));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(401).body(createErrorResponse("No autenticado", "Sesión no encontrada", 401));
            }

            String username = authentication.getName();
            HttpSession session = request.getSession(false);

            // Invalidar sesión
            if (session != null) {
                session.invalidate();
            }

            // Limpiar contexto de seguridad
            SecurityContextHolder.clearContext();

            log.info(">>> [LOGOUT]  Sesión cerrada exitosamente — usuario: '{}'", username);

            return ResponseEntity.ok(createResponse(true, "Sesión cerrada exitosamente", null, 200));

        } catch (Exception e) {
            log.error(">>> [LOGOUT]  Error en logout: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error en logout", e.getMessage(), 500));
        }
    }

    @PutMapping("/toggle/{idUser}")
    public ResponseEntity<?> toggleUserStatus(@PathVariable int idUser) {
        try {
            log.info(">>> [TOGGLE] Cambiando estado del usuario ID: {}", idUser);
            User updatedUser = userService.toggleUserStatus(idUser);
            ToggleResponseDTO response = new ToggleResponseDTO(updatedUser.getIdUser(), updatedUser.getUsernameUser(), updatedUser.isStatusUser());
            return ResponseEntity.ok(createResponse(true, "Estado del usuario actualizado", response, 200));
        } catch (RuntimeException e) {
            log.warn(">>> [TOGGLE]  Error: {}", e.getMessage());
            return ResponseEntity.status(404).body(createErrorResponse("Error al cambiar estado", e.getMessage(), 404));
        } catch (Exception e) {
            log.error(">>> [TOGGLE]  Error inesperado: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error inesperado", e.getMessage(), 500));
        }
    }

    @GetMapping("/status/{idUser}")
    public ResponseEntity<?> getUserStatus(@PathVariable int idUser) {
        try {
            log.info(">>> [STATUS] Obteniendo estado del usuario ID: {}", idUser);
            var userOpt = userService.getUserById(idUser);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                StatusResponseDTO response = new StatusResponseDTO(user.getIdUser(), user.getUsernameUser(), user.isStatusUser());
                return ResponseEntity.ok(createResponse(true, "Estado de usuario obtenido", response, 200));
            }
            log.warn(">>> [STATUS] Usuario no encontrado - ID: {}", idUser);
            return ResponseEntity.status(404).body(createErrorResponse("Usuario no encontrado", "ID: " + idUser, 404));
        } catch (Exception e) {
            log.error(">>> [STATUS]  Error: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error al obtener estado", e.getMessage(), 500));
        }
    }

    private Map<String, Object> createResponse(boolean success, String message, Object data, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", message);
        response.put("data", data);
        response.put("status", status);
        return response;
    }

    private Map<String, Object> createErrorResponse(String message, String error, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("error", error);
        response.put("status", status);
        return response;
    }
}

