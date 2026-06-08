package pe.edu.untels.servicesimplements;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pe.edu.untels.dtos.LibroDTO;
import pe.edu.untels.dtos.LibroResponseDTO;
import pe.edu.untels.dtos.LibroApiExternaDTO;
import pe.edu.untels.entities.Libro;
import pe.edu.untels.exceptions.ValidationException;
import pe.edu.untels.repositories.ILibroRepository;
import pe.edu.untels.servicesinterfaces.ILibroService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de libro.
 * Incluye operaciones CRUD, búsquedas y consulta a OpenLibrary API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LibroServiceImplement implements ILibroService {

    private final ILibroRepository libroRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    private static final String OPENLIBRARY_API_URL = "https://openlibrary.org/api/books";

    @Override
    @Transactional(readOnly = true)
    public Page<LibroResponseDTO> obtenerTodos(Pageable pageable) {
        log.info("Obteniendo todos los libros con paginación: {}", pageable);
        Page<Libro> page = libroRepository.findAll(pageable);
        return mapToPageResponseDTO(page);
    }

    @Override
    @Transactional(readOnly = true)
    public LibroResponseDTO obtenerPorId(Integer idLibro) {
        log.info("Obteniendo libro por ID: {}", idLibro);
        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new ValidationException(
                        "Libro no encontrado con ID: " + idLibro));
        return convertToResponseDTO(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public LibroResponseDTO obtenerPorIsbn(String isbn) {
        log.info("Obteniendo libro por ISBN: {}", isbn);
        Libro libro = libroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ValidationException(
                        "Libro no encontrado con ISBN: " + isbn));
        return convertToResponseDTO(libro);
    }

    @Override
    public LibroResponseDTO crear(LibroDTO libroDTO) {
        log.info("Creando nuevo libro con ISBN: {}", libroDTO.getIsbn());

        // Validar ISBN único
        if (libroRepository.existsByIsbn(libroDTO.getIsbn())) {
            throw new ValidationException(
                    "Ya existe un libro registrado con el ISBN: " + libroDTO.getIsbn());
        }

        // Validar campos obligatorios
        validarLibroDTO(libroDTO);

        // Validar stock
        if (libroDTO.getStock() < 0 || libroDTO.getStockTotal() < 0) {
            throw new ValidationException("El stock no puede ser negativo");
        }

        // Crear la entidad
        Libro libro = modelMapper.map(libroDTO, Libro.class);
        libro.setStatusLibro(true);
        libro.setCreadoPor("BIBLIOTECARIO");
        libro.setActualizadoPor("BIBLIOTECARIO");

        // Normalizar año de publicación
        if (libroDTO.getAnoPublicacion() != null && libroDTO.getAnoPublicacion() > 0) {
            libro.setAnoPublicacion(libroDTO.getAnoPublicacion());
        }

        Libro saved = libroRepository.save(libro);
        log.info("Libro creado exitosamente con ID: {}", saved.getIdLibro());

        return convertToResponseDTO(saved);
    }

    @Override
    public LibroResponseDTO actualizar(Integer idLibro, LibroDTO libroDTO) {
        log.info("Actualizando libro con ID: {}", idLibro);

        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new ValidationException(
                        "Libro no encontrado con ID: " + idLibro));

        // Si cambia el ISBN, verificar que sea único
        if (!libro.getIsbn().equals(libroDTO.getIsbn()) &&
            libroRepository.existsByIsbn(libroDTO.getIsbn())) {
            throw new ValidationException(
                    "Ya existe un libro registrado con el ISBN: " + libroDTO.getIsbn());
        }

        // Validar campos obligatorios
        validarLibroDTO(libroDTO);

        // Validar stock
        if (libroDTO.getStock() < 0 || libroDTO.getStockTotal() < 0) {
            throw new ValidationException("El stock no puede ser negativo");
        }

        // Actualizar campos
        libro.setIsbn(libroDTO.getIsbn());
        libro.setTitulo(libroDTO.getTitulo());
        libro.setAutor(libroDTO.getAutor());
        libro.setEditorial(libroDTO.getEditorial());
        libro.setCategoria(libroDTO.getCategoria());
        libro.setAnoPublicacion(libroDTO.getAnoPublicacion());
        libro.setDescripcion(libroDTO.getDescripcion());
        libro.setUrlPortada(libroDTO.getUrlPortada());
        libro.setStock(libroDTO.getStock());
        libro.setStockTotal(libroDTO.getStockTotal());
        libro.setPaginas(libroDTO.getPaginas());
        libro.setIdioma(libroDTO.getIdioma());
        libro.setActualizadoPor("BIBLIOTECARIO");
        libro.setFechaActualizacion(LocalDateTime.now());

        Libro updated = libroRepository.save(libro);
        log.info("Libro actualizado exitosamente: {}", updated.getIdLibro());

        return convertToResponseDTO(updated);
    }

    @Override
    public void eliminar(Integer idLibro) {
        log.info("Eliminando libro con ID: {}", idLibro);

        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new ValidationException(
                        "Libro no encontrado con ID: " + idLibro));

        // Soft delete
        libro.setStatusLibro(false);
        libro.setActualizadoPor("BIBLIOTECARIO");
        libro.setFechaActualizacion(LocalDateTime.now());
        libroRepository.save(libro);

        log.info("Libro eliminado exitosamente (soft delete): {}", idLibro);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LibroResponseDTO> buscarPorTitulo(String titulo, Pageable pageable) {
        log.info("Buscando libros por título: {}", titulo);
        Page<Libro> page = libroRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        return mapToPageResponseDTO(page);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LibroResponseDTO> buscarPorCategoria(String categoria, Pageable pageable) {
        log.info("Buscando libros por categoría: {}", categoria);
        Page<Libro> page = libroRepository.findByCategoria(categoria, pageable);
        return mapToPageResponseDTO(page);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LibroResponseDTO> buscarPorAutor(String autor, Pageable pageable) {
        log.info("Buscando libros por autor: {}", autor);
        Page<Libro> page = libroRepository.findByAutorContainingIgnoreCase(autor, pageable);
        return mapToPageResponseDTO(page);
    }

    @Override
    @Transactional(readOnly = true)
    public LibroApiExternaDTO consultarOpenLibrary(String isbn) {
        log.info("Consultando OpenLibrary API para ISBN: {}", isbn);

        try {
            // Construir URL de OpenLibrary
            String url = String.format("%s?bibkeys=ISBN:%s&format=json&jscmd=data", 
                    OPENLIBRARY_API_URL, isbn);
            
            log.debug("URL de OpenLibrary: {}", url);

            // Realizar petición GET
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || response.isEmpty()) {
                log.warn("OpenLibrary no retornó datos para ISBN: {}", isbn);
                return null;
            }

            // La respuesta viene con key "ISBN:XXX"
            String key = "ISBN:" + isbn;
            if (!response.containsKey(key)) {
                log.warn("ISBN no encontrado en OpenLibrary: {}", isbn);
                return null;
            }

            // Mapear respuesta
            @SuppressWarnings("unchecked")
            Map<String, Object> libroData = (Map<String, Object>) response.get(key);

            // Convertir manualmente (sin usar ModelMapper para API externa)
            LibroApiExternaDTO dto = mapFromOpenLibraryResponse(libroData);
            log.info("Datos recuperados exitosamente de OpenLibrary para ISBN: {}", isbn);

            return dto;

        } catch (Exception e) {
            log.error("Error consultando OpenLibrary API para ISBN: {}", isbn, e);
            throw new ValidationException(
                    "Error consultando OpenLibrary para ISBN: " + isbn, e);
        }
    }

    @Override
    public LibroResponseDTO crearDesdeOpenLibrary(String isbn, LibroDTO libroDTO) {
        log.info("Creando libro desde OpenLibrary para ISBN: {}", isbn);

        // Validar ISBN único
        if (libroRepository.existsByIsbn(isbn)) {
            throw new ValidationException(
                    "Ya existe un libro registrado con el ISBN: " + isbn);
        }

        // Consultar OpenLibrary
        LibroApiExternaDTO datosOpenLibrary = consultarOpenLibrary(isbn);

        if (datosOpenLibrary == null) {
            throw new ValidationException(
                    "No se pudieron recuperar datos de OpenLibrary para el ISBN: " + isbn);
        }

        // Enriquecer libroDTO con datos de OpenLibrary
        libroDTO.setIsbn(isbn);
        if (libroDTO.getTitulo() == null || libroDTO.getTitulo().isEmpty()) {
            libroDTO.setTitulo(datosOpenLibrary.getTitulo());
        }
        if (libroDTO.getAutor() == null || libroDTO.getAutor().isEmpty()) {
            if (datosOpenLibrary.getAutores() != null && !datosOpenLibrary.getAutores().isEmpty()) {
                libroDTO.setAutor(datosOpenLibrary.getAutores().get(0).getNombre());
            }
        }
        if (libroDTO.getEditorial() == null || libroDTO.getEditorial().isEmpty()) {
            if (datosOpenLibrary.getEditoriales() != null && !datosOpenLibrary.getEditoriales().isEmpty()) {
                libroDTO.setEditorial(datosOpenLibrary.getEditoriales().get(0).getNombre());
            }
        }
        if (libroDTO.getDescripcion() == null || libroDTO.getDescripcion().isEmpty()) {
            libroDTO.setDescripcion(datosOpenLibrary.getDescripcion());
        }
        if (libroDTO.getPaginas() == null) {
            libroDTO.setPaginas(datosOpenLibrary.getPaginas());
        }
        if (libroDTO.getUrlPortada() == null || libroDTO.getUrlPortada().isEmpty()) {
            if (datosOpenLibrary.getPortada() != null && datosOpenLibrary.getPortada().getGrande() != null) {
                libroDTO.setUrlPortada(datosOpenLibrary.getPortada().getGrande());
            }
        }

        // Crear libro con datos enriquecidos
        return crear(libroDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isbnExiste(String isbn) {
        log.debug("Verificando existencia de ISBN: {}", isbn);
        return libroRepository.existsByIsbn(isbn);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LibroResponseDTO> obtenerLibrosDisponibles(Pageable pageable) {
        log.info("Obteniendo libros disponibles para préstamo");
        Page<Libro> page = libroRepository.findByStatusLibroAndStockGreaterThan(true, 0, pageable);
        return mapToPageResponseDTO(page);
    }

    /**
     * Convierte una entidad Libro a un DTO de respuesta.
     */
    private LibroResponseDTO convertToResponseDTO(Libro libro) {
        LibroResponseDTO dto = modelMapper.map(libro, LibroResponseDTO.class);
        dto.setDisponible(libro.getStock() > 0);
        return dto;
    }

    /**
     * Convierte una Page de Libros a una Page de DTOs de respuesta.
     */
    private Page<LibroResponseDTO> mapToPageResponseDTO(Page<Libro> page) {
        List<LibroResponseDTO> content = page.getContent().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    /**
     * Valida los campos obligatorios del DTO de Libro.
     */
    private void validarLibroDTO(LibroDTO libroDTO) {
        if (libroDTO.getIsbn() == null || libroDTO.getIsbn().trim().isEmpty()) {
            throw new ValidationException("El ISBN es obligatorio");
        }
        if (libroDTO.getTitulo() == null || libroDTO.getTitulo().trim().isEmpty()) {
            throw new ValidationException("El título es obligatorio");
        }
        if (libroDTO.getAutor() == null || libroDTO.getAutor().trim().isEmpty()) {
            throw new ValidationException("El autor es obligatorio");
        }
    }

    /**
     * Mapea manualmente la respuesta de OpenLibrary a LibroApiExternaDTO.
     * Esto es necesario porque JSONObject devuelve tipos genéricos.
     */
    @SuppressWarnings("unchecked")
    private LibroApiExternaDTO mapFromOpenLibraryResponse(Map<String, Object> data) {
        LibroApiExternaDTO dto = new LibroApiExternaDTO();

        dto.setTitulo((String) data.get("title"));

        // Procesar autores
        if (data.get("authors") instanceof List) {
            List<Map<String, Object>> autoresList = (List<Map<String, Object>>) data.get("authors");
            dto.setAutores(autoresList.stream()
                    .map(a -> new LibroApiExternaDTO.AutorOpenLibrary(
                            (String) a.get("name"),
                            (String) a.get("url")))
                    .collect(Collectors.toList()));
        }

        // Procesar editoriales
        if (data.get("publishers") instanceof List) {
            List<Map<String, Object>> editoralesList = (List<Map<String, Object>>) data.get("publishers");
            dto.setEditoriales(editoralesList.stream()
                    .map(e -> new LibroApiExternaDTO.PublisherOpenLibrary((String) e.get("name")))
                    .collect(Collectors.toList()));
        }

        dto.setFechaPublicacion((String) data.get("publish_date"));
        dto.setDescripcion((String) data.get("description"));
        Number pages = (Number) data.get("number_of_pages");
        if (pages != null) {
            dto.setPaginas(pages.intValue());
        }

        // Procesar portada
        if (data.get("cover") instanceof Map) {
            Map<String, String> coverMap = (Map<String, String>) data.get("cover");
            dto.setPortada(new LibroApiExternaDTO.CoverOpenLibrary(
                    coverMap.get("small"),
                    coverMap.get("medium"),
                    coverMap.get("large")));
        }

        // Procesar idiomas
        if (data.get("languages") instanceof List) {
            List<Map<String, Object>> idiomasList = (List<Map<String, Object>>) data.get("languages");
            dto.setIdiomas(idiomasList.stream()
                    .map(i -> new LibroApiExternaDTO.LanguageOpenLibrary(
                            (String) i.get("key"),
                            (String) i.get("name")))
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}

