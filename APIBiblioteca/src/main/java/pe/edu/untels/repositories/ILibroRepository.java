package pe.edu.untels.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.Libro;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Libro.
 * Proporciona operaciones CRUD y búsquedas especializadas.
 */
@Repository
public interface ILibroRepository extends JpaRepository<Libro, Integer> {

    /**
     * Buscar libro por ISBN exacto.
     */
    Optional<Libro> findByIsbn(String isbn);

    /**
     * Verificar si existe un libro con ISBN específico.
     */
    Boolean existsByIsbn(String isbn);

    /**
     * Buscar libros por título (búsqueda parcial, insensible a mayúsculas).
     */
    Page<Libro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    /**
     * Buscar libros por categoría exacta.
     */
    Page<Libro> findByCategoria(String categoria, Pageable pageable);

    /**
     * Buscar libros por categoría y estado.
     */
    Page<Libro> findByCategoriaAndStatusLibro(String categoria, Boolean status, Pageable pageable);

    /**
     * Buscar libros por autor (búsqueda parcial, insensible a mayúsculas).
     */
    Page<Libro> findByAutorContainingIgnoreCase(String autor, Pageable pageable);

    /**
     * Obtener todos los libros activos.
     */
    Page<Libro> findByStatusLibro(Boolean status, Pageable pageable);

    /**
     * Buscar libros por estado y disponibilidad (stock > 0) con paginación.
     */
    Page<Libro> findByStatusLibroAndStockGreaterThan(Boolean status, Integer stock, Pageable pageable);
}


