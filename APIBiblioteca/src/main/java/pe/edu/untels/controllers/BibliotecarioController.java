package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.entities.Bibliotecario;
import pe.edu.untels.servicesinterfaces.IBibliotecarioService;

@RestController
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    @Autowired
    private IBibliotecarioService service;

    @PostMapping
    public Bibliotecario crear(@RequestBody Bibliotecario b) {
        return service.registrar(b);
    }
}
