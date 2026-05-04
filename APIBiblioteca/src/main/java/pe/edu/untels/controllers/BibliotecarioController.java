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

    @PutMapping("/{id}")
    public Bibliotecario editar(@PathVariable Long id, @RequestBody Bibliotecario b) {
        return service.editar(id, b);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.deshabilitar(id);
    }
}
