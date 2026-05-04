package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.entities.Bibliotecario;

import java.util.List;

public interface IBibliotecarioService {
    Bibliotecario registrar(Bibliotecario b);
    List<Bibliotecario> listar();
}
