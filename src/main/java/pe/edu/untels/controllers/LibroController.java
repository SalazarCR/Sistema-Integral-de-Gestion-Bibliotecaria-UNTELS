package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import pe.edu.untels.entities.Libro;
import pe.edu.untels.repositories.ILibroRepository;
import pe.edu.untels.servicesinterfaces.ILibroService;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private ILibroRepository libroRepository;

    @Autowired
    private ILibroService libroService;

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

    @PutMapping("/{id}")
    public String actualizarLibro(@PathVariable int id,
                                  @RequestBody Libro libroActualizado) {

        Libro libro = libroService.actualizarLibro(id, libroActualizado);

        if (libro == null) {
            return "Libro no encontrado";
        }

        return "Libro actualizado correctamente";
    }
}