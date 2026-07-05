package pe.edu.untels.servicesinterfaces;

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

    List<Libro> buscarPorAutor(String autor);

    Optional<Libro> buscarPorIsbn(String isbn);

    boolean existeIsbn(String isbn);

    List<Libro> buscarConStockBajo(int umbral);
}
