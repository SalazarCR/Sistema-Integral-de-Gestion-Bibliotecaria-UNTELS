package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
import pe.edu.untels.servicesinterfaces.IUserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO response = userService.login(loginRequestDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @GetMapping("/validate-role/{idUser}")
    public ResponseEntity<?> validateRole(@PathVariable int idUser) {
        try {
            var userOpt = userService.getUserById(idUser);
            if (userOpt.isPresent()) {
                String role = userOpt.get().getRole().getNameRole();
                return ResponseEntity.ok("Rol: " + role);
            }
            return ResponseEntity.status(404).body("Usuario no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
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
}

