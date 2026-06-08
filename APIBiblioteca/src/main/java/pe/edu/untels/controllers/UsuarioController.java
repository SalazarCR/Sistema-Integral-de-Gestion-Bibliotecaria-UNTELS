package pe.edu.untels.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.dtos.UsuarioDTO;
import pe.edu.untels.entities.Usuario;
import pe.edu.untels.servicesinterfaces.IUsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/lista")
    public ResponseEntity<List<UsuarioDTO>> listar() {
        ModelMapper mapper = new ModelMapper();

        List<UsuarioDTO> lista = usuarioService.list()
                .stream()
                .map(usuario -> mapper.map(usuario, UsuarioDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ModelMapper mapper = new ModelMapper();
        Optional<Usuario> usuario = usuarioService.listId(id);

        if (usuario.isPresent()) {
            UsuarioDTO dto = mapper.map(usuario.get(), UsuarioDTO.class);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado");
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UsuarioDTO>> buscarPorRol(@PathVariable String rol) {
        ModelMapper mapper = new ModelMapper();

        List<UsuarioDTO> lista = usuarioService.buscarPorRol(rol)
                .stream()
                .map(usuario -> mapper.map(usuario, UsuarioDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@RequestBody UsuarioDTO dto) {
        ModelMapper mapper = new ModelMapper();

        if (usuarioService.existeUsername(dto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un usuario con ese username");
        }

        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setPassword(passwordEncoder.encode(dto.getPassword())); // ¡SEGURO!
        Usuario guardado = usuarioService.insert(usuario);
        UsuarioDTO responseDTO = mapper.map(guardado, UsuarioDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/actualiza")
    public ResponseEntity<String> actualizar(@RequestBody UsuarioDTO dto) {
        Optional<Usuario> existente = usuarioService.listId(dto.getIdUsuario());

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        Usuario usuario = existente.get();
        usuario.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword())); // ¡SEGURO!
        }
        usuario.setCodigo(dto.getCodigo());
        usuario.setCarnet(dto.getCarnet());
        usuario.setDni(dto.getDni());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(dto.getRol());
        usuario.setCarrera(dto.getCarrera());
        usuario.setCiclo(dto.getCiclo());
        usuario.setEstado(dto.getEstado());

        usuarioService.edit(usuario);

        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        Optional<Usuario> usuario = usuarioService.listId(id);

        if (usuario.isPresent()) {
            usuarioService.delete(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado");
    }
}
