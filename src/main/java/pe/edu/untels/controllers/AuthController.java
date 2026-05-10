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
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO,
                                   HttpServletRequest request) {
        try {

            // ✅ DTO CORRECTO
            String identifier = (loginRequestDTO.getUsername() != null && !loginRequestDTO.getUsername().isEmpty())
                    ? loginRequestDTO.getUsername()
                    : loginRequestDTO.getEmail();

            String password = loginRequestDTO.getPassword();

            log.info(">>> [LOGIN] Intento de login — usuario/email: '{}' — IP: {}",
                    identifier, request.getRemoteAddr());

            // Paso 1: Validación por servicio
            LoginResponseDTO response = userService.login(loginRequestDTO);

            // Paso 2: Spring Security
            try {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(identifier, password);

                if (authProvider != null) {
                    Authentication authenticated = authProvider.authenticate(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authenticated);

                    log.info(">>> [LOGIN] Contexto creado — usuario: '{}'",
                            authenticated.getName());
                } else {
                    request.getSession(true);
                    log.info(">>> [LOGIN] Sesión creada sin provider");
                }

            } catch (AuthenticationException ex) {
                log.warn(">>> [LOGIN] Warning auth provider: {}", ex.getMessage());
                request.getSession(true);
            }

            log.info(">>> [LOGIN] LOGIN EXITOSO — usuario: '{}'", identifier);

            return ResponseEntity.ok(
                    createResponse(true, "Login exitoso", response, 200)
            );

        } catch (RuntimeException e) {
            log.warn(">>> [LOGIN] Error: {}", e.getMessage());
            return ResponseEntity.status(401)
                    .body(createErrorResponse("Login fallido", e.getMessage(), 401));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401)
                    .body(createErrorResponse("No autenticado", "Sin sesión", 401));
        }

        String username = auth.getName();

        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        SecurityContextHolder.clearContext();

        log.info(">>> [LOGOUT] Usuario cerrado: '{}'", username);

        return ResponseEntity.ok(
                createResponse(true, "Logout exitoso", null, 200)
        );
    }

    @PutMapping("/toggle/{idUser}")
    public ResponseEntity<?> toggleUserStatus(@PathVariable int idUser) {

        User user = userService.toggleUserStatus(idUser);

        ToggleResponseDTO response = new ToggleResponseDTO(
                user.getIdUser(),
                user.getUsernameUser(),
                user.isStatusUser()
        );

        return ResponseEntity.ok(
                createResponse(true, "Estado actualizado", response, 200)
        );
    }

    @GetMapping("/status/{idUser}")
    public ResponseEntity<?> getUserStatus(@PathVariable int idUser) {

        return userService.getUserById(idUser)
                .map(user -> {
                    StatusResponseDTO response = new StatusResponseDTO(
                            user.getIdUser(),
                            user.getUsernameUser(),
                            user.isStatusUser()
                    );
                    return ResponseEntity.ok(
                            createResponse(true, "Estado obtenido", response, 200)
                    );
                })
                .orElseGet(() ->
                        ResponseEntity.status(404)
                                .body(createErrorResponse("Usuario no encontrado", "ID: " + idUser, 404))
                );
    }

    // ========================
    // RESPUESTAS
    // ========================

    private Map<String, Object> createResponse(boolean success, String message, Object data, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("message", message);
        map.put("data", data);
        map.put("status", status);
        return map;
    }

    private Map<String, Object> createErrorResponse(String message, String error, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("message", message);
        map.put("error", error);
        map.put("status", status);
        return map;
    }
}