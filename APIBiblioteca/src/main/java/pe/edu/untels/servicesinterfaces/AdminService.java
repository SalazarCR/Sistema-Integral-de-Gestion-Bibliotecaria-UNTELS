package pe.edu.untels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.Admin;
import pe.edu.untels.repositories.IAdminRepository;
import pe.edu.untels.servicesinterfaces.IAdminService;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private IAdminRepository repo;

    @Override
    public Admin guardar(Admin admin) {
        return repo.save(admin);
    }

    @Override
    public List<Admin> listar() {
        return repo.findByActivoTrue();
    }

    @Override
    public Admin actualizar(Long id, Admin admin) {
        Admin existente = repo.findById(id).orElseThrow();
        existente.setNombre(admin.getNombre());
        existente.setEmail(admin.getEmail());
        return repo.save(existente);
    }

    @Override
    public void deshabilitar(Long id) {
        Admin admin = repo.findById(id).orElseThrow();
        admin.setActivo(false);
        repo.save(admin);
    }
}
