package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.entities.Admin;

import java.util.List;

public interface IAdminService {

    Admin guardar(Admin admin);

    List<Admin> listar();

    Admin actualizar(Long id, Admin admin);

    void deshabilitar(Long id);
}