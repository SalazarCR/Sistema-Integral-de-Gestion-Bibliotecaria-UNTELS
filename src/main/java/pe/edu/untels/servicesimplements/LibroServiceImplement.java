package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.untels.entities.Libro;
import pe.edu.untels.repositories.ILibroRepository;
import pe.edu.untels.servicesinterfaces.ILibroService;

@Service
public class LibroServiceImplement implements ILibroService {

    @Autowired
    private ILibroRepository libroRepository;

    @Override
    public Libro actualizarLibro(int id, Libro libroActualizado) {

        Libro libro = libroRepository.findById(id).orElse(null);

        if (libro == null) {
            return null;
        }

        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setDescripcion(libroActualizado.getDescripcion());
        libro.setIsbn(libroActualizado.getIsbn());
        libro.setStock(libroActualizado.getStock());

        return libroRepository.save(libro);
    }
}