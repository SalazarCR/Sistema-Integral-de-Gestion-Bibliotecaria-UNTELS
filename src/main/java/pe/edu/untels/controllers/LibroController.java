package pe.edu.untels.controllers;

import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.LibroDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @GetMapping
    public List<LibroDTO> listarLibros() {

        List<LibroDTO> lista = new ArrayList<>();

        return lista;
    }

    @PostMapping
    public String registrarLibro(@RequestBody LibroDTO dto) {

        return "Libro registrado correctamente";
    }
}