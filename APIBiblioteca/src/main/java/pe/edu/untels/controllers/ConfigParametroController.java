package pe.edu.untels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.ApiResponseDTO;
import pe.edu.untels.dtos.ConfigParametroRequest;
import pe.edu.untels.dtos.ConfigParametroResponse;
import pe.edu.untels.exceptions.ParametroValidationException;
import pe.edu.untels.servicesinterfaces.IConfigParametroService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/config")
@RequiredArgsConstructor
@Tag(name = "Configuración", description = "Endpoints para la gestión de parámetros de configuración del sistema")
public class ConfigParametroController {

    private final IConfigParametroService configParametroService;

    @Operation(summary = "Obtener todos los parámetros de configuración",
            description = "Retrieves all system configuration parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de parámetros obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConfigParametroResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDTO> obtenerTodos() {
        log.info("GET /api/v1/config - Obtaining all configuration parameters");
        try {
            List<ConfigParametroResponse> parametros = configParametroService.obtenerTodos();
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Parámetros obtenidos exitosamente")
                    .data(parametros)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Error obtaining configuration parameters", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al obtener los parámetros")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    @Operation(summary = "Obtener parámetro de configuración por nombre",
            description = "Retrieves a specific configuration parameter by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parámetro encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConfigParametroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Parámetro no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{nombre}")
    public ResponseEntity<ApiResponseDTO> obtenerPorNombre(
            @Parameter(description = "Nombre del parámetro", required = true)
            @PathVariable String nombre) {
        log.info("GET /api/v1/config/{} - Obtaining configuration parameter", nombre);
        try {
            ConfigParametroResponse parametro = configParametroService.obtenerPorNombre(nombre);
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Parámetro obtenido exitosamente")
                    .data(parametro)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (ParametroValidationException e) {
            log.warn("Configuration parameter not found: {}", nombre);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error obtaining configuration parameter", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al obtener el parámetro")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    @Operation(summary = "Crear nuevo parámetro de configuración",
            description = "Creates a new system configuration parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parámetro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o parámetro ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO> crear(
            @Valid @RequestBody ConfigParametroRequest request) {
        log.info("POST /api/v1/config - Creating new configuration parameter: {}", request.getNombre());
        try {
            ConfigParametroResponse parametro = configParametroService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.builder()
                            .success(true)
                            .message("Parámetro creado exitosamente")
                            .data(parametro)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (ParametroValidationException e) {
            log.warn("Validation error creating configuration parameter: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error creating configuration parameter", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al crear el parámetro")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    @Operation(summary = "Actualizar parámetro de configuración",
            description = "Updates an existing system configuration parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parámetro actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Parámetro no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{nombre}")
    public ResponseEntity<ApiResponseDTO> actualizar(
            @Parameter(description = "Nombre del parámetro", required = true)
            @PathVariable String nombre,
            @Valid @RequestBody ConfigParametroRequest request) {
        log.info("PUT /api/v1/config/{} - Updating configuration parameter", nombre);
        try {
            ConfigParametroResponse parametro = configParametroService.actualizar(nombre, request);
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Parámetro actualizado exitosamente")
                    .data(parametro)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (ParametroValidationException e) {
            log.warn("Error updating configuration parameter: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error updating configuration parameter", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al actualizar el parámetro")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    @Operation(summary = "Eliminar parámetro de configuración",
            description = "Deletes a system configuration parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parámetro eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Parámetro no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{nombre}")
    public ResponseEntity<ApiResponseDTO> eliminar(
            @Parameter(description = "Nombre del parámetro", required = true)
            @PathVariable String nombre) {
        log.info("DELETE /api/v1/config/{} - Deleting configuration parameter", nombre);
        try {
            configParametroService.eliminar(nombre);
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Parámetro eliminado exitosamente")
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (ParametroValidationException e) {
            log.warn("Configuration parameter not found: {}", nombre);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error deleting configuration parameter", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al eliminar el parámetro")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    @Operation(summary = "Obtener días de préstamo",
            description = "Gets the configured number of days for book loans")
    @ApiResponse(responseCode = "200", description = "Valor obtenido exitosamente")
    @GetMapping("/valores/dias-prestamo")
    public ResponseEntity<ApiResponseDTO> obtenerDiasPrestamo() {
        log.info("GET /api/v1/config/valores/dias-prestamo - Getting days for loans");
        try {
            int dias = configParametroService.obtenerDiasPrestamo();
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Días de préstamo obtenidos")
                    .data(dias)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Error obtaining days for loans", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al obtener los días de préstamo")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    @Operation(summary = "Obtener límite de préstamos",
            description = "Gets the configured maximum number of simultaneous loans")
    @ApiResponse(responseCode = "200", description = "Valor obtenido exitosamente")
    @GetMapping("/valores/limite-prestamos")
    public ResponseEntity<ApiResponseDTO> obtenerLimitePrestamos() {
        log.info("GET /api/v1/config/valores/limite-prestamos - Getting loan limit");
        try {
            int limite = configParametroService.obtenerLimitePrestamos();
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Límite de préstamos obtenido")
                    .data(limite)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Error obtaining loan limit", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al obtener el límite de préstamos")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    @Operation(summary = "Obtener multa por retraso",
            description = "Gets the configured daily fine for overdue books")
    @ApiResponse(responseCode = "200", description = "Valor obtenido exitosamente")
    @GetMapping("/valores/multa-retraso")
    public ResponseEntity<ApiResponseDTO> obtenerMultaRetraso() {
        log.info("GET /api/v1/config/valores/multa-retraso - Getting overdue fine");
        try {
            double multa = configParametroService.obtenerMultaRetraso();
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Multa por retraso obtenida")
                    .data(multa)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Error obtaining overdue fine", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al obtener la multa por retraso")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }
}

