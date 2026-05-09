package pe.edu.untels.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.ConfigParametroDTO;
import pe.edu.untels.dtos.LimitePrestamosRequestDTO;
import pe.edu.untels.servicesinterfaces.IConfigParametroService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigParametroController {

    private static final Logger log = LoggerFactory.getLogger(ConfigParametroController.class);

    @Autowired
    private IConfigParametroService configService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<?> obtenerConfiguracion() {
        try {
            log.info(">>> [CONFIG] Obteniendo configuración de parámetros");
            ConfigParametroDTO config = configService.obtenerConfiguracion();
            return ResponseEntity.ok(createResponse(true, "Configuración obtenida exitosamente", config, 200));
        } catch (Exception e) {
            log.error(">>> [CONFIG] Error al obtener configuración: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error al obtener configuración", e.getMessage(), 500));
        }
    }

    @PostMapping("/limite-prestamos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrarLimitePrestamos(@RequestBody LimitePrestamosRequestDTO request) {
        try {
            log.info(">>> [CONFIG] Registrando límite de préstamos: {}", request.getLimitePrestamos());
            if (request.getLimitePrestamos() <= 0) {
                return ResponseEntity.status(400).body(createErrorResponse("El límite de préstamos debe ser mayor a 0", "Valor inválido", 400));
            }
            ConfigParametroDTO config = configService.registrarLimitePrestamos(request.getLimitePrestamos());
            return ResponseEntity.status(201).body(createResponse(true, "Límite de préstamos registrado exitosamente", config, 201));
        } catch (Exception e) {
            log.error(">>> [CONFIG] Error al registrar límite de préstamos: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error al registrar límite de préstamos", e.getMessage(), 500));
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
