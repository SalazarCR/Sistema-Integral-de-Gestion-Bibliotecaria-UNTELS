package pe.edu.untels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.ApiResponseDTO;
import pe.edu.untels.dtos.LibroDTO;
import pe.edu.untels.dtos.LibroResponseDTO;
import pe.edu.untels.dtos.LibroApiExternaDTO;
import pe.edu.untels.exceptions.ValidationException;
import pe.edu.untels.servicesinterfaces.ILibroService;

import java.time.LocalDateTime;

/**
 * Controlador REST para la gestión del catálogo de libros.
 * Proporciona endpoints para CRUD, búsquedas y consultas a OpenLibrary.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/libros")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Libros", description = "Endpoints para la gestión del catálogo de libros")
@SecurityRequirement(name = "bearerAuth")
public class LibroController {

    private final ILibroService libroService;

    /**
     * Endpoint: GET /api/v1/libros
     * Obtener todos los libros con paginación.
     */
    @GetMapping
    @Operation(summary = "Obtener todos los libros",
            description = "Retorna una lista paginada de todos los libros registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libros obtenidos exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> obtenerTodos(
            @Parameter(description = "Número de página (0-indexed)") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") 
            @RequestParam(defaultValue = "10") int size) {
        try {
            log.info("GET /api/v1/libros - Obteniendo todos los libros, página: {}, tamaño: {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            Page<LibroResponseDTO> libros = libroService.obtenerTodos(pageable);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Libros obtenidos exitosamente")
                    .data(libros)
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Error obteniendo libros", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al obtener libros: " + e.getMessage())
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: GET /api/v1/libros/{id}
     * Obtener un libro por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener libro por ID",
            description = "Retorna los detalles de un libro específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> obtenerPorId(
            @Parameter(description = "ID del libro") 
            @PathVariable Integer id) {
        try {
            log.info("GET /api/v1/libros/{} - Obteniendo libro por ID", id);
            LibroResponseDTO libro = libroService.obtenerPorId(id);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Libro obtenido exitosamente")
                    .data(libro)
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (ValidationException e) {
            log.warn("Libro no encontrado: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(404)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error obteniendo libro por ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al obtener el libro")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: GET /api/v1/libros/isbn/{isbn}
     * Obtener un libro por ISBN.
     */
    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Obtener libro por ISBN",
            description = "Retorna los detalles de un libro buscando por ISBN exacto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "400", description = "ISBN inválido"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> obtenerPorIsbn(
            @Parameter(description = "ISBN del libro (10 o 13 dígitos)") 
            @PathVariable String isbn) {
        try {
            log.info("GET /api/v1/libros/isbn/{} - Obteniendo libro por ISBN", isbn);

            // Validar formato ISBN
            if (isbn == null || isbn.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDTO.builder()
                                .success(false)
                                .message("ISBN es requerido")
                                .statusCode(400)
                                .timestamp(LocalDateTime.now())
                                .build());
            }

            LibroResponseDTO libro = libroService.obtenerPorIsbn(isbn);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Libro obtenido exitosamente")
                    .data(libro)
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (ValidationException e) {
            log.warn("Libro no encontrado con ISBN: {}", isbn);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(404)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error obteniendo libro por ISBN", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al obtener el libro")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: POST /api/v1/libros
     * Crear un nuevo libro.
     */
    @PostMapping
    @Operation(summary = "Crear nuevo libro",
            description = "Registra un nuevo libro en el catálogo del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o ISBN duplicado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> crear(
            @Valid @RequestBody LibroDTO libroDTO) {
        try {
            log.info("POST /api/v1/libros - Creando nuevo libro con ISBN: {}", libroDTO.getIsbn());
            LibroResponseDTO libroCreado = libroService.crear(libroDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.builder()
                            .success(true)
                            .message("Libro creado exitosamente")
                            .data(libroCreado)
                            .statusCode(201)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (ValidationException e) {
            log.warn("Error validando datos del libro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error creando libro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al crear el libro")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: PUT /api/v1/libros/{id}
     * Actualizar un libro existente.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar libro",
            description = "Modifica los datos de un libro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID del libro") 
            @PathVariable Integer id,
            @Valid @RequestBody LibroDTO libroDTO) {
        try {
            log.info("PUT /api/v1/libros/{} - Actualizando libro", id);
            LibroResponseDTO libroActualizado = libroService.actualizar(id, libroDTO);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Libro actualizado exitosamente")
                    .data(libroActualizado)
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (ValidationException e) {
            log.warn("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error actualizando libro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al actualizar el libro")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: DELETE /api/v1/libros/{id}
     * Deletar un libro (soft delete).
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar libro",
            description = "Marca un libro como eliminado (soft delete, no se elimina físicamente)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del libro") 
            @PathVariable Integer id) {
        try {
            log.info("DELETE /api/v1/libros/{} - Eliminando libro", id);
            libroService.eliminar(id);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Libro eliminado exitosamente")
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (ValidationException e) {
            log.warn("Libro no encontrado: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(404)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error eliminando libro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al eliminar el libro")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: GET /api/v1/libros/buscar/titulo
     * Buscar libros por título (búsqueda parcial).
     */
    @GetMapping("/buscar/titulo")
    @Operation(summary = "Buscar libros por título",
            description = "Busca libros cuyo título contenga el texto especificado (búsqueda parcial, insensible a mayúsculas)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> buscarPorTitulo(
            @Parameter(description = "Texto de búsqueda") 
            @RequestParam String titulo,
            @Parameter(description = "Número de página (0-indexed)") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") 
            @RequestParam(defaultValue = "10") int size) {
        try {
            log.info("GET /api/v1/libros/buscar/titulo - Buscando libros por título: {}", titulo);

            if (titulo == null || titulo.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDTO.builder()
                                .success(false)
                                .message("El parámetro 'titulo' es requerido")
                                .statusCode(400)
                                .timestamp(LocalDateTime.now())
                                .build());
            }

            Pageable pageable = PageRequest.of(page, size);
            Page<LibroResponseDTO> resultado = libroService.buscarPorTitulo(titulo, pageable);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Búsqueda realizada exitosamente")
                    .data(resultado)
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Error buscando por título", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al buscar por título")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: GET /api/v1/libros/buscar/categoria
     * Buscar libros por categoría.
     */
    @GetMapping("/buscar/categoria")
    @Operation(summary = "Buscar libros por categoría",
            description = "Retorna todos los libros que pertenecen a la categoría especificada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> buscarPorCategoria(
            @Parameter(description = "Categoría de libros") 
            @RequestParam String categoria,
            @Parameter(description = "Número de página (0-indexed)") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") 
            @RequestParam(defaultValue = "10") int size) {
        try {
            log.info("GET /api/v1/libros/buscar/categoria - Buscando libros por categoría: {}", categoria);

            if (categoria == null || categoria.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDTO.builder()
                                .success(false)
                                .message("El parámetro 'categoria' es requerido")
                                .statusCode(400)
                                .timestamp(LocalDateTime.now())
                                .build());
            }

            Pageable pageable = PageRequest.of(page, size);
            Page<LibroResponseDTO> resultado = libroService.buscarPorCategoria(categoria, pageable);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Búsqueda realizada exitosamente")
                    .data(resultado)
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Error buscando por categoría", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al buscar por categoría")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: GET /api/v1/libros/buscar/autor
     * Buscar libros por autor (búsqueda parcial).
     */
    @GetMapping("/buscar/autor")
    @Operation(summary = "Buscar libros por autor",
            description = "Busca libros cuyo autor contenga el texto especificado (búsqueda parcial, insensible a mayúsculas)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> buscarPorAutor(
            @Parameter(description = "Texto de búsqueda")
            @RequestParam String autor,
            @Parameter(description = "Número de página (0-indexed)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página")
            @RequestParam(defaultValue = "10") int size) {
        try {
            log.info("GET /api/v1/libros/buscar/autor - Buscando libros por autor: {}", autor);

            if (autor == null || autor.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDTO.builder()
                                .success(false)
                                .message("El parámetro 'autor' es requerido")
                                .statusCode(400)
                                .timestamp(LocalDateTime.now())
                                .build());
            }

            Pageable pageable = PageRequest.of(page, size);
            Page<LibroResponseDTO> resultado = libroService.buscarPorAutor(autor, pageable);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Búsqueda realizada exitosamente")
                    .data(resultado)
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Error buscando por autor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al buscar por autor")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: GET /api/v1/libros/openlibrary/{isbn}
     * Consultar información de OpenLibrary por ISBN.
     */
    @GetMapping("/openlibrary/{isbn}")
    @Operation(summary = "Consultar OpenLibrary por ISBN",
            description = "Consulta la API externa OpenLibrary para obtener información enriquecida de un libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos obtenidos exitosamente"),
            @ApiResponse(responseCode = "404", description = "ISBN no encontrado en OpenLibrary"),
            @ApiResponse(responseCode = "400", description = "ISBN inválido"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error al consultar OpenLibrary")
    })
    public ResponseEntity<?> consultarOpenLibrary(
            @Parameter(description = "ISBN para consultar (10 o 13 dígitos)") 
            @PathVariable String isbn) {
        try {
            log.info("GET /api/v1/libros/openlibrary/{} - Consultando OpenLibrary", isbn);

            if (isbn == null || isbn.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDTO.builder()
                                .success(false)
                                .message("ISBN es requerido")
                                .statusCode(400)
                                .timestamp(LocalDateTime.now())
                                .build());
            }

            LibroApiExternaDTO datos = libroService.consultarOpenLibrary(isbn);

            if (datos == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.builder()
                                .success(false)
                                .message("ISBN no encontrado en OpenLibrary: " + isbn)
                                .statusCode(404)
                                .timestamp(LocalDateTime.now())
                                .build());
            }

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Datos obtenidos exitosamente de OpenLibrary")
                    .data(datos)
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (ValidationException e) {
            log.error("Error consultando OpenLibrary: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error consultando OpenLibrary", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al consultar OpenLibrary")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: POST /api/v1/libros/desde-openlibrary
     * Crear libro desde datos de OpenLibrary.
     */
    @PostMapping("/desde-openlibrary")
    @Operation(summary = "Crear libro desde OpenLibrary",
            description = "Consulta OpenLibrary y crea un nuevo libro enriquecido con datos de la API externa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o ISBN no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> crearDesdeOpenLibrary(
            @Parameter(description = "ISBN para consultar") 
            @RequestParam String isbn,
            @Valid @RequestBody LibroDTO libroDTO) {
        try {
            log.info("POST /api/v1/libros/desde-openlibrary - Creando libro desde OpenLibrary con ISBN: {}", isbn);
            LibroResponseDTO libroCreado = libroService.crearDesdeOpenLibrary(isbn, libroDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.builder()
                            .success(true)
                            .message("Libro creado exitosamente desde OpenLibrary")
                            .data(libroCreado)
                            .statusCode(201)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (ValidationException e) {
            log.warn("Error validando datos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error creando libro desde OpenLibrary", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al crear libro desde OpenLibrary")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Endpoint: GET /api/v1/libros/disponibles
     * Obtener libros disponibles para préstamo.
     */
    @GetMapping("/disponibles")
    @Operation(summary = "Obtener libros disponibles",
            description = "Retorna una lista paginada de libros que tienen stock disponible para préstamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libros obtenidos exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> obtenerLibrosDisponibles(
            @Parameter(description = "Número de página (0-indexed)") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") 
            @RequestParam(defaultValue = "10") int size) {
        try {
            log.info("GET /api/v1/libros/disponibles - Obteniendo libros disponibles");
            Pageable pageable = PageRequest.of(page, size);
            Page<LibroResponseDTO> libros = libroService.obtenerLibrosDisponibles(pageable);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Libros disponibles obtenidos exitosamente")
                    .data(libros)
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("Error obteniendo libros disponibles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message("Error al obtener libros disponibles")
                            .statusCode(500)
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }
}

