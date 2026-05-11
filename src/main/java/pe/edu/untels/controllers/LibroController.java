package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import pe.edu.untels.entities.Libro;
import pe.edu.untels.repositories.ILibroRepository;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private ILibroRepository libroRepository;

    @GetMapping("/paginado")
    public Page<Libro> listarLibrosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return libroRepository.findAll(PageRequest.of(page, size));
    }

    @PostMapping
    public String registrarLibro(@RequestBody Libro libro) {

        libroRepository.save(libro);

        return "Libro registrado correctamente";
    }
}