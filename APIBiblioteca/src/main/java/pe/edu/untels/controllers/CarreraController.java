package pe.edu.untels.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.CarreraDTO;
import pe.edu.untels.servicesinterfaces.ICarreraService;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/carreras")
public class CarreraController {
    private static final Logger log = LoggerFactory.getLogger(CarreraController.class);

    @Autowired
    private ICarreraService carreraService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCarrera(@RequestBody CarreraDTO carreraDTO) {
        try {
            log.info(">>> [CARRERA] Creando carrera: {}", carreraDTO.getNameCarrera());
            CarreraDTO createdCarrera = carreraService.createCarrera(carreraDTO);
            return ResponseEntity.status(201).body(createResponse(true, "Carrera creada exitosamente", createdCarrera, 201));
        } catch (Exception e) {
            log.error(">>> [CARRERA] ❌ Error al crear: {}", e.getMessage());
            return ResponseEntity.status(400).body(createErrorResponse("Error al crear carrera", e.getMessage(), 400));
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<List<CarreraDTO>> listCarreras() {
        try {
            log.info(">>> [CARRERA] Listando todas las carreras");
            List<CarreraDTO> carreras = carreraService.listCarreras();
            return ResponseEntity.ok(carreras);
        } catch (Exception e) {
            log.error(">>> [CARRERA] ❌ Error al listar: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/activas")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<List<CarreraDTO>> listActiveCarreras() {
        try {
            log.info(">>> [CARRERA] Listando carreras activas");
            List<CarreraDTO> carreras = carreraService.listActiveCarreras();
            return ResponseEntity.ok(carreras);
        } catch (Exception e) {
            log.error(">>> [CARRERA] ❌ Error al listar activas: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{idCarrera}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<?> getCarrera(@PathVariable int idCarrera) {
        try {
            log.info(">>> [CARRERA] Obteniendo carrera ID: {}", idCarrera);
            var carreraOpt = carreraService.getCarreraById(idCarrera);
            if (carreraOpt.isPresent()) {
                return ResponseEntity.ok(carreraOpt.get());
            }
            return ResponseEntity.status(404).body(createErrorResponse("Carrera no encontrada", "ID: " + idCarrera, 404));
        } catch (Exception e) {
            log.error(">>> [CARRERA] ❌ Error: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error al obtener carrera", e.getMessage(), 500));
        }
    }

    @PutMapping("/{idCarrera}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCarrera(@PathVariable int idCarrera, @RequestBody CarreraDTO carreraDTO) {
        try {
            log.info(">>> [CARRERA] Actualizando carrera ID: {}", idCarrera);
            CarreraDTO updatedCarrera = carreraService.updateCarrera(idCarrera, carreraDTO);
            return ResponseEntity.ok(createResponse(true, "Carrera actualizada exitosamente", updatedCarrera, 200));
        } catch (Exception e) {
            log.error(">>> [CARRERA] ❌ Error al actualizar: {}", e.getMessage());
            return ResponseEntity.status(400).body(createErrorResponse("Error al actualizar carrera", e.getMessage(), 400));
        }
    }

    @DeleteMapping("/{idCarrera}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCarrera(@PathVariable int idCarrera) {
        try {
            log.info(">>> [CARRERA] Eliminando carrera ID: {}", idCarrera);
            carreraService.deleteCarrera(idCarrera);
            return ResponseEntity.ok(createResponse(true, "Carrera eliminada correctamente", null, 200));
        } catch (Exception e) {
            log.error(">>> [CARRERA] ❌ Error al eliminar: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error al eliminar carrera", e.getMessage(), 500));
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

