package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.entities.Bibliotecario;
import pe.edu.untels.servicesinterfaces.IBibliotecarioService;

import java.util.List;

@RestController
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    @Autowired
    private IBibliotecarioService service;

    @PostMapping
    public Bibliotecario crear(@RequestBody Bibliotecario b) {
        return service.registrar(b);
    }

    @GetMapping
    public List<Bibliotecario> listar() {
        return service.listar();
    }
}
