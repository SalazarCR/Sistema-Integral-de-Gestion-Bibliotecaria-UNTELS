package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.ApiResponseDTO;
import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
import pe.edu.untels.exceptions.AuthException;
import pe.edu.untels.exceptions.ValidationException;
import pe.edu.untels.servicesinterfaces.IAuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            // Validar que no sean nulos
            if (loginRequestDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO(false, "Datos de login requeridos", null, 400));
            }

            // Llamar al servicio de autenticación
            LoginResponseDTO response = authService.login(loginRequestDTO);

            // Si llegamos aquí, el login fue exitoso (200 OK)
            return ResponseEntity.status(HttpStatus.OK)
                .body(response);

        } catch (ValidationException e) {
            // Error de validación (400 Bad Request)
            ApiResponseDTO errorResponse = new ApiResponseDTO(false, e.getMessage(), null, 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (AuthException e) {
            // Error de autenticación (401 Unauthorized)
            ApiResponseDTO errorResponse = new ApiResponseDTO(false, e.getMessage(), null, 401);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            // Error genérico (500 Internal Server Error)
            ApiResponseDTO errorResponse = new ApiResponseDTO(false, "Error interno del servidor", null, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

