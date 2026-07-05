package pe.edu.untels.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.dtos.JwtRequest;
import pe.edu.untels.dtos.JwtResponse;
import pe.edu.untels.entities.ConfiguracionBiblioteca;
import pe.edu.untels.entities.Usuario;
import pe.edu.untels.securities.JwtTokenUtil;
import pe.edu.untels.securities.JwtUserDetailsService;
import pe.edu.untels.servicesinterfaces.IConfiguracionBibliotecaService;
import pe.edu.untels.servicesinterfaces.IUsuarioService;

import java.util.Optional;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private IConfiguracionBibliotecaService configuracionService;

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) {
        UserDetails userDetails;

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("El usuario se encuentra inactivo. Contacte al administrador.");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos.");
        }

        boolean esAdmin = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        Optional<ConfiguracionBiblioteca> configOpt = configuracionService.obtener();
        if (!esAdmin && configOpt.isPresent() && configOpt.get().isModoMant()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("El sistema se encuentra en mantenimiento. Intente mas tarde.");
        }

        Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());
        final String token = jwtTokenUtil.generateToken(usuario.getUsername(), usuario.getRol());

        return ResponseEntity.ok(new JwtResponse(token, usuario.getIdUsuario(), usuario.getUsername(), usuario.getNombre(), usuario.getRol()));
    }
}
