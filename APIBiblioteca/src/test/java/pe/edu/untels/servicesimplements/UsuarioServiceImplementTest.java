package pe.edu.untels.servicesimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.untels.entities.Usuario;
import pe.edu.untels.repositories.IUsuarioRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de UsuarioServiceImplement.
 *
 * Nota: la regla HUF01.8 ("no se permite registrar nuevos administradores") y la
 * validacion de username duplicado (409) se aplican en UsuarioController.registrar(),
 * no en este servicio. Ese servicio solo expone existeUsername()/buscarPorUsername()
 * como utilidades que el controller consulta. La logica de esas reglas se prueba en
 * UsuarioControllerTest.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplementTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImplement usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setUsername("jperez");
        usuario.setPassword("encriptado");
        usuario.setCodigo("2020100001");
        usuario.setNombre("Juan Perez");
        usuario.setEmail("jperez@untels.edu.pe");
        usuario.setRol("ESTUDIANTE");
        usuario.setEstado("ACTIVO");
    }

    @Test
    void list_devuelveTodosLosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        assertEquals(1, usuarioService.list().size());
    }

    @Test
    void insert_delegaEnRepositoryYDevuelveElGuardado() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario guardado = usuarioService.insert(usuario);

        assertEquals(usuario, guardado);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void listId_encontrado() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        assertTrue(usuarioService.listId(1).isPresent());
    }

    @Test
    void listId_noEncontrado() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertTrue(usuarioService.listId(99).isEmpty());
    }

    @Test
    void edit_delegaEnRepositorySave() {
        usuarioService.edit(usuario);

        verify(usuarioRepository).save(usuario);
    }

    @Test
    void delete_delegaEnRepositoryDeleteById() {
        usuarioService.delete(1);

        verify(usuarioRepository).deleteById(1);
    }

    @Test
    void buscarPorRol_delegaEnFindByRol() {
        when(usuarioRepository.findByRol("BIBLIOTECARIO")).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.buscarPorRol("BIBLIOTECARIO");

        assertEquals(1, resultado.size());
        verify(usuarioRepository).findByRol("BIBLIOTECARIO");
    }

    @Test
    void buscarPorEstado_delegaEnFindByEstado() {
        when(usuarioRepository.findByEstado("ACTIVO")).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.buscarPorEstado("ACTIVO");

        assertEquals(1, resultado.size());
        verify(usuarioRepository).findByEstado("ACTIVO");
    }

    @Test
    void existeUsername_true_cuandoYaRegistrado() {
        when(usuarioRepository.existsByUsername("jperez")).thenReturn(true);

        assertTrue(usuarioService.existeUsername("jperez"));
    }

    @Test
    void existeUsername_false_cuandoDisponible() {
        when(usuarioRepository.existsByUsername("nuevo")).thenReturn(false);

        assertFalse(usuarioService.existeUsername("nuevo"));
    }

    @Test
    void buscarPorUsername_encontrado_devuelveUsuario() {
        when(usuarioRepository.findByUsername("jperez")).thenReturn(usuario);

        Usuario resultado = usuarioService.buscarPorUsername("jperez");

        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    void buscarPorUsername_noEncontrado_devuelveNull() {
        when(usuarioRepository.findByUsername("inexistente")).thenReturn(null);

        assertNull(usuarioService.buscarPorUsername("inexistente"));
    }
}
