package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.Libro;

import java.util.List;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Integer> {

    List<Libro> findByCategoria(String categoria);

    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    boolean existsByIsbn(String isbn);
}
