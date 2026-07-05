package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.Libro;
import pe.edu.untels.repositories.ILibroRepository;
import pe.edu.untels.servicesinterfaces.ILibroService;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImplement implements ILibroService {

    @Autowired
    private ILibroRepository libroRepository;

    @Override
    public List<Libro> list() {
        return libroRepository.findAll();
    }

    @Override
    public Libro insert(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public Optional<Libro> listId(int id) {
        return libroRepository.findById(id);
    }

    @Override
    public void edit(Libro libro) {
        libroRepository.save(libro);
    }

    @Override
    public void delete(int id) {
        libroRepository.deleteById(id);
    }

    @Override
    public List<Libro> buscarPorCategoria(String categoria) {
        return libroRepository.findByCategoria(categoria);
    }

    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    public boolean existeIsbn(String isbn) {
        return libroRepository.existsByIsbn(isbn);
    }

    @Override
    public List<Libro> buscarPorAutor(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor);
    }

    @Override
    public Optional<Libro> buscarPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }

    @Override
    public List<Libro> buscarConStockBajo(int umbral) {
        return libroRepository.findByStockLessThanEqual(umbral);
    }
}
