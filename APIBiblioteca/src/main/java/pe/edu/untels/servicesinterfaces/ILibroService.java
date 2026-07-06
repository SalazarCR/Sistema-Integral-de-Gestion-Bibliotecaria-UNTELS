package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.dtos.LibroApiExternaDTO;
import pe.edu.untels.entities.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibroService {

    List<Libro> list();

    Libro insert(Libro libro);

    Optional<Libro> listId(int id);

    void edit(Libro libro);

    void delete(int id);

    List<Libro> buscarPorCategoria(String categoria);

    List<Libro> buscarPorTitulo(String titulo);

    boolean existeIsbn(String isbn);

    Libro registrarLibroPorIsbn(String isbn);

    LibroApiExternaDTO buscarPorIsbnEnApi(String isbn);
}
