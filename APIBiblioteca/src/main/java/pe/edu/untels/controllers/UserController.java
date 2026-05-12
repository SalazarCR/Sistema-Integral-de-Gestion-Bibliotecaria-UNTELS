package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.ApiResponseDTO;
import pe.edu.untels.dtos.UserDTO;
import pe.edu.untels.exceptions.ValidationException;
import pe.edu.untels.servicesinterfaces.IUserService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody UserDTO userDTO) {
        try {
            ApiResponseDTO response = userService.registrarUsuario(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO(false, e.getMessage(), null, 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO(false, "Error interno del servidor", null, 500));
        }
    }

    @GetMapping
    public ResponseEntity<?> listarUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            ApiResponseDTO response = userService.listarUsuarios(page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO(false, "Error interno del servidor", null, 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        try {
            ApiResponseDTO response = userService.actualizarUsuario(id, userDTO);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO(false, e.getMessage(), null, 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO(false, "Error interno del servidor", null, 500));
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> toggleEstadoUsuario(@PathVariable Integer id) {
        try {
            ApiResponseDTO response = userService.toggleEstadoUsuario(id);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO(false, e.getMessage(), null, 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO(false, "Error interno del servidor", null, 500));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarUsuario(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String dni) {
        try {
            if (username != null && !username.trim().isEmpty()) {
                return ResponseEntity.ok(userService.buscarPorUsername(username));
            }
            if (dni != null && !dni.trim().isEmpty()) {
                return ResponseEntity.ok(userService.buscarPorDni(dni));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO(false, "Debe indicar username o DNI para la búsqueda", null, 400));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO(false, e.getMessage(), null, 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO(false, "Error interno del servidor", null, 500));
        }
    }

    @GetMapping("/filtrar")
    public ResponseEntity<?> filtrarUsuarios(
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            ApiResponseDTO response = userService.filtrarUsuarios(rol, estado, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO(false, "Error interno del servidor", null, 500));
        }
    }
}
