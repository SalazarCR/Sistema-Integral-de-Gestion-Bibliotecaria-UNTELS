package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.entities.Admin;
import pe.edu.untels.servicesinterfaces.IAdminService;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private IAdminService service;

    // CREATE
    @PostMapping
    public Admin crear(@RequestBody Admin admin){
        return service.guardar(admin);
    }

    // READ
    @GetMapping
    public List<Admin> listar(){
        return service.listar();
    }

    // UPDATE
    @PutMapping("/{id}")
    public Admin actualizar(@PathVariable Long id, @RequestBody Admin admin){
        return service.actualizar(id, admin);
    }

    // DELETE LÓGICO
    @DeleteMapping("/{id}")
    public void deshabilitar(@PathVariable Long id){
        service.deshabilitar(id);
    }
}