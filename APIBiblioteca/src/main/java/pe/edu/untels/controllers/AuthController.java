package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
import pe.edu.untels.dtos.StatusResponseDTO;
import pe.edu.untels.dtos.ToggleResponseDTO;
import pe.edu.untels.entities.User;
import pe.edu.untels.servicesinterfaces.IUserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO response = userService.login(loginRequestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(LoginResponseDTO.error(e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam int idUser) {
        try {
            if (userService.isUserActive(idUser)) {
                return ResponseEntity.ok("Logout exitoso");
            }
            return ResponseEntity.status(401).body("Usuario no activo");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en logout");
        }
    }

    @PutMapping("/toggle/{idUser}")
    public ResponseEntity<?> toggleUserStatus(@PathVariable int idUser) {
        try {
            User updatedUser = userService.toggleUserStatus(idUser);
            return ResponseEntity.ok(new ToggleResponseDTO(updatedUser.getIdUser(), updatedUser.getUsernameUser(), updatedUser.isStatusUser()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al cambiar estado: " + e.getMessage());
        }
    }

    @GetMapping("/status/{idUser}")
    public ResponseEntity<?> getUserStatus(@PathVariable int idUser) {
        try {
            var userOpt = userService.getUserById(idUser);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                return ResponseEntity.ok(new StatusResponseDTO(user.getIdUser(), user.getUsernameUser(), user.isStatusUser()));
            }
            return ResponseEntity.status(404).body("Usuario no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

