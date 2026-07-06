package pe.edu.untels.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.dtos.LibroDTO;
import pe.edu.untels.entities.Libro;
import pe.edu.untels.servicesinterfaces.ILibroService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private ILibroService libroService;

    @GetMapping("/lista")
    public ResponseEntity<List<LibroDTO>> listar() {
        ModelMapper mapper = new ModelMapper();

        List<LibroDTO> lista = libroService.list()
                .stream()
                .map(libro -> mapper.map(libro, LibroDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ModelMapper mapper = new ModelMapper();
        Optional<Libro> libro = libroService.listId(id);

        if (libro.isPresent()) {
            LibroDTO dto = mapper.map(libro.get(), LibroDTO.class);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Libro no encontrado");
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDTO>> buscarPorTitulo(@RequestParam String titulo) {
        ModelMapper mapper = new ModelMapper();

        List<LibroDTO> lista = libroService.buscarPorTitulo(titulo)
                .stream()
                .map(libro -> mapper.map(libro, LibroDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<LibroDTO>> buscarPorCategoria(@PathVariable String categoria) {
        ModelMapper mapper = new ModelMapper();

        List<LibroDTO> lista = libroService.buscarPorCategoria(categoria)
                .stream()
                .map(libro -> mapper.map(libro, LibroDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/registrar-por-isbn")
    public ResponseEntity<?> registrarPorIsbn(@RequestParam String isbn) {
        try {
            Libro libroGuardado = libroService
                    .registrarLibroPorIsbn(isbn);
            ModelMapper mapper = new ModelMapper();
            LibroDTO responseDTO = mapper.map(libroGuardado, LibroDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@RequestBody LibroDTO dto) {
        ModelMapper mapper = new ModelMapper();

        if (libroService.existeIsbn(dto.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un libro con ese ISBN");
        }

        Libro libro = mapper.map(dto, Libro.class);

        if (libro.getStockTotal() == 0) {
            libro.setStockTotal(libro.getStock());
        }

        Libro guardado = libroService.insert(libro);
        LibroDTO responseDTO = mapper.map(guardado, LibroDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/actualiza")
    public ResponseEntity<String> actualizar(@RequestBody LibroDTO dto) {
        Optional<Libro> existente = libroService.listId(dto.getIdLibro());

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro no encontrado");
        }

        Libro libro = existente.get();
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        libro.setEditorial(dto.getEditorial());
        libro.setAnio(dto.getAnio());
        libro.setCategoria(dto.getCategoria());
        libro.setStock(dto.getStock());
        libro.setStockTotal(dto.getStockTotal());
        libro.setDescripcion(dto.getDescripcion());
        libro.setRecurso(dto.getRecurso());

        libroService.edit(libro);

        return ResponseEntity.ok("Libro actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        Optional<Libro> libro = libroService.listId(id);

        if (libro.isPresent()) {
            libroService.delete(id);
            return ResponseEntity.ok("Libro eliminado correctamente");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Libro no encontrado");
    }
}
