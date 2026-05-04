package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.entities.Bibliotecario;

import java.util.List;

public interface IBibliotecarioService {
    Bibliotecario registrar(Bibliotecario b);
    List<Bibliotecario> listar();
    Bibliotecario editar(Long id, Bibliotecario b);
    void deshabilitar(Long id);
}
