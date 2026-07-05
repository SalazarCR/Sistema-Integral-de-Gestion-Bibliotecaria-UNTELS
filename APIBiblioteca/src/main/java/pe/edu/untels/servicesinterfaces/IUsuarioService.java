package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> list();

    Usuario insert(Usuario usuario);

    Optional<Usuario> listId(int id);

    void edit(Usuario usuario);

    void delete(int id);

    List<Usuario> buscarPorRol(String rol);

    List<Usuario> buscarPorEstado(String estado);

    boolean existeUsername(String username);

    Usuario buscarPorUsername(String username);

    List<Usuario> buscar(String texto);
}
