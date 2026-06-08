package pe.edu.untels.servicesinterfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.untels.dtos.LibroDTO;
import pe.edu.untels.dtos.LibroResponseDTO;
import pe.edu.untels.dtos.LibroApiExternaDTO;

/**
 * Interfaz de servicios para la gestión del catálogo de libros.
 * Define operaciones CRUD, búsquedas y consultas a APIs externas.
 */
public interface ILibroService {

    /**
     * Obtener todos los libros con paginación.
     */
    Page<LibroResponseDTO> obtenerTodos(Pageable pageable);

    /**
     * Obtener un libro por su ID.
     */
    LibroResponseDTO obtenerPorId(Integer idLibro);

    /**
     * Obtener un libro por su ISBN.
     */
    LibroResponseDTO obtenerPorIsbn(String isbn);

    /**
     * Crear un nuevo libro.
     */
    LibroResponseDTO crear(LibroDTO libroDTO);

    /**
     * Actualizar un libro existente.
     */
    LibroResponseDTO actualizar(Integer idLibro, LibroDTO libroDTO);

    /**
     * Eliminar un libro (soft delete - cambiar estado).
     */
    void eliminar(Integer idLibro);

    /**
     * Buscar libros por título (búsqueda parcial).
     */
    Page<LibroResponseDTO> buscarPorTitulo(String titulo, Pageable pageable);

    /**
     * Buscar libros por categoría.
     */
    Page<LibroResponseDTO> buscarPorCategoria(String categoria, Pageable pageable);

    /**
     * Buscar libros por autor (búsqueda parcial).
     */
    Page<LibroResponseDTO> buscarPorAutor(String autor, Pageable pageable);

    /**
     * Consultar información de un libro en OpenLibrary por ISBN.
     * Retorna datos enriquecidos de la API externa.
     */
    LibroApiExternaDTO consultarOpenLibrary(String isbn);

    /**
     * Crear un libro a partir de información de OpenLibrary.
     * Consulta la API externa y luego registra el libro.
     */
    LibroResponseDTO crearDesdeOpenLibrary(String isbn, LibroDTO libroDTO);

    /**
     * Verificar si un ISBN es único en el sistema.
     */
    Boolean isbnExiste(String isbn);

    /**
     * Obtener libros disponibles para préstamo (stock > 0).
     */
    Page<LibroResponseDTO> obtenerLibrosDisponibles(Pageable pageable);
}

