package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.CarreraDTO;
import pe.edu.untels.servicesinterfaces.ICarreraService;
import java.util.List;

@RestController
@RequestMapping("/api/carreras")
public class CarreraController {
    @Autowired
    private ICarreraService carreraService;

    @PostMapping
    public ResponseEntity<?> createCarrera(@RequestBody CarreraDTO carreraDTO) {
        try {
            CarreraDTO createdCarrera = carreraService.createCarrera(carreraDTO);
            return ResponseEntity.status(201).body(createdCarrera);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CarreraDTO>> listCarreras() {
        try {
            List<CarreraDTO> carreras = carreraService.listCarreras();
            return ResponseEntity.ok(carreras);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/activas")
    public ResponseEntity<List<CarreraDTO>> listActiveCarreras() {
        try {
            List<CarreraDTO> carreras = carreraService.listActiveCarreras();
            return ResponseEntity.ok(carreras);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{idCarrera}")
    public ResponseEntity<?> getCarrera(@PathVariable int idCarrera) {
        try {
            var carreraOpt = carreraService.getCarreraById(idCarrera);
            if (carreraOpt.isPresent()) {
                return ResponseEntity.ok(carreraOpt.get());
            }
            return ResponseEntity.status(404).body("Carrera no encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{idCarrera}")
    public ResponseEntity<?> updateCarrera(@PathVariable int idCarrera, @RequestBody CarreraDTO carreraDTO) {
        try {
            CarreraDTO updatedCarrera = carreraService.updateCarrera(idCarrera, carreraDTO);
            return ResponseEntity.ok(updatedCarrera);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idCarrera}")
    public ResponseEntity<?> deleteCarrera(@PathVariable int idCarrera) {
        try {
            carreraService.deleteCarrera(idCarrera);
            return ResponseEntity.ok("Carrera eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

