package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.entities.User;
import pe.edu.untels.servicesinterfaces.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    // 🔘 TOGGLE ACTIVO / INACTIVO
    @PutMapping("/toggle/{id}")
    public ResponseEntity<User> toggleUser(@PathVariable int id) {
        User updatedUser = userService.toggleUserStatus(id);
        return ResponseEntity.ok(updatedUser);
    }

    // 🔘 CAMBIO MANUAL DE ESTADO (true/false)
    @PutMapping("/status/{id}")
    public ResponseEntity<String> changeStatus(
            @PathVariable int id,
            @RequestParam boolean status) {

        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setStatusUser(status);
        userService.updateUser(user);

        return ResponseEntity.ok("Estado actualizado correctamente");
    }
}