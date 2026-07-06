package pe.edu.untels.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.untels.dtos.UsuarioDTO;
import pe.edu.untels.entities.Usuario;
import pe.edu.untels.servicesinterfaces.IUsuarioService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de UsuarioController.registrar(), que es donde realmente
 * viven dos reglas de negocio clave:
 * - HUF01.8: no se permite crear usuarios con rol ADMIN via este endpoint (403).
 *   (Confirmado: SI esta implementada, en el controller, no en el service.)
 * - Username unico (409 CONFLICT) antes de guardar.
 * Tambien cubre cambiarPassword() (validacion de la contraseña actual).
 */
@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private IUsuarioService usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioDTO dtoNuevoUsuario(String rol) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsername("jperez");
        dto.setPassword("clave123");
        dto.setCodigo("2020100001");
        dto.setNombre("Juan Perez");
        dto.setEmail("jperez@untels.edu.pe");
        dto.setRol(rol);
        dto.setEstado("ACTIVO");
        return dto;
    }

    @Test
    void registrar_rolAdmin_retorna403YNoGuardaNada() {
        UsuarioDTO dto = dtoNuevoUsuario("ADMIN");

        ResponseEntity<?> respuesta = usuarioController.registrar(dto);

        assertEquals(HttpStatus.FORBIDDEN, respuesta.getStatusCode());
        verify(usuarioService, never()).existeUsername(any());
        verify(usuarioService, never()).insert(any());
    }

    @Test
    void registrar_rolAdminEnMinusculas_tambienSeBloquea() {
        UsuarioDTO dto = dtoNuevoUsuario("admin");

        ResponseEntity<?> respuesta = usuarioController.registrar(dto);

        assertEquals(HttpStatus.FORBIDDEN, respuesta.getStatusCode());
        verify(usuarioService, never()).insert(any());
    }

    @Test
    void registrar_usernameDuplicado_retorna409YNoGuarda() {
        UsuarioDTO dto = dtoNuevoUsuario("ESTUDIANTE");
        when(usuarioService.existeUsername("jperez")).thenReturn(true);

        ResponseEntity<?> respuesta = usuarioController.registrar(dto);

        assertEquals(HttpStatus.CONFLICT, respuesta.getStatusCode());
        verify(usuarioService, never()).insert(any());
    }

    @Test
    void registrar_exitoso_encriptaLaPasswordAntesDeGuardar() {
        UsuarioDTO dto = dtoNuevoUsuario("ESTUDIANTE");
        when(usuarioService.existeUsername("jperez")).thenReturn(false);
        when(passwordEncoder.encode("clave123")).thenReturn("HASH_ENCRIPTADO");
        when(usuarioService.insert(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<?> respuesta = usuarioController.registrar(dto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioService).insert(captor.capture());
        assertEquals("HASH_ENCRIPTADO", captor.getValue().getPassword());
    }

    @Test
    void cambiarPassword_actualIncorrecta_retorna400() {
        Usuario usuario = new Usuario();
        usuario.setUsername("jperez");
        usuario.setPassword("HASH_ACTUAL");

        Principal principal = () -> "jperez";
        when(usuarioService.buscarPorUsername("jperez")).thenReturn(usuario);
        when(passwordEncoder.matches("incorrecta", "HASH_ACTUAL")).thenReturn(false);

        Map<String, String> body = new HashMap<>();
        body.put("passwordActual", "incorrecta");
        body.put("passwordNueva", "nueva123");

        ResponseEntity<?> respuesta = usuarioController.cambiarPassword(body, principal);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        verify(usuarioService, never()).edit(any());
    }

    @Test
    void cambiarPassword_exitoso_actualizaConLaNuevaPasswordEncriptada() {
        Usuario usuario = new Usuario();
        usuario.setUsername("jperez");
        usuario.setPassword("HASH_ACTUAL");

        Principal principal = () -> "jperez";
        when(usuarioService.buscarPorUsername("jperez")).thenReturn(usuario);
        when(passwordEncoder.matches("actual123", "HASH_ACTUAL")).thenReturn(true);
        when(passwordEncoder.encode("nueva123")).thenReturn("HASH_NUEVO");

        Map<String, String> body = new HashMap<>();
        body.put("passwordActual", "actual123");
        body.put("passwordNueva", "nueva123");

        ResponseEntity<?> respuesta = usuarioController.cambiarPassword(body, principal);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("HASH_NUEVO", usuario.getPassword());
        verify(usuarioService).edit(usuario);
    }

    @Test
    void cambiarPassword_camposVacios_retorna400() {
        Principal principal = () -> "jperez";
        Map<String, String> body = new HashMap<>();
        body.put("passwordActual", "");
        body.put("passwordNueva", "nueva123");

        ResponseEntity<?> respuesta = usuarioController.cambiarPassword(body, principal);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        verify(usuarioService, never()).buscarPorUsername(any());
    }

    @Test
    void actualizarMiPerfil_usuarioNoEncontrado_retorna404() {
        Principal principal = () -> "desconocido";
        when(usuarioService.buscarPorUsername("desconocido")).thenReturn(null);

        ResponseEntity<?> respuesta = usuarioController.actualizarMiPerfil(new HashMap<>(), principal);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void actualizarMiPerfil_soloActualizaNombreEmailYTelefono() {
        Usuario usuario = new Usuario();
        usuario.setUsername("jperez");
        usuario.setNombre("Nombre Viejo");
        usuario.setRol("ESTUDIANTE");
        usuario.setEstado("ACTIVO");

        Principal principal = () -> "jperez";
        when(usuarioService.buscarPorUsername("jperez")).thenReturn(usuario);

        Map<String, String> body = new HashMap<>();
        body.put("nombre", "Nombre Nuevo");
        body.put("email", "nuevo@untels.edu.pe");

        ResponseEntity<?> respuesta = usuarioController.actualizarMiPerfil(body, principal);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Nombre Nuevo", usuario.getNombre());
        assertEquals("nuevo@untels.edu.pe", usuario.getEmail());
        assertEquals("ESTUDIANTE", usuario.getRol(), "no debe poder escalar su propio rol desde este endpoint");
        verify(usuarioService).edit(usuario);
    }

    @Test
    void listId_noEncontrado_retorna404() {
        when(usuarioService.listId(99)).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = usuarioController.buscarPorId(99);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}
