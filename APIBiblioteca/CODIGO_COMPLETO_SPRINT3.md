# 💻 Código Completo del Sprint 3 - Proyecto APIBiblioteca

Este documento contiene la estructura completa del código fuente en **Java Spring Boot**, organizada por integrante. Cada sección incluye la Entidad, Repositorio, Servicio, Controlador y DTOs correspondientes, manteniendo las relaciones e importaciones necesarias para el funcionamiento del sistema.

---

## 👤 1. CESAR - Seguridad y Autenticación JWT

Responsable de la protección de la API, generación de tokens y configuración de filtros de seguridad.

### 🔐 securities/JwtTokenUtil.java
```java
package pe.edu.untels.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret:UNTELS2026SecretKeyForJWTTokenBiblioBibliotecaAPISpringBoot}")
    private String secret;
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey()).compact();
    }
    public Boolean validateToken(String token, String username) {
        final String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}
```

### 🛡️ securities/WebSecurityConfig.java
```java
package pe.edu.untels.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired private JwtUserDetailsService jwtUserDetailsService;
    @Autowired private JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public static PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
```

### 🎮 controllers/JwtAuthenticationController.java
```java
package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.*;
import pe.edu.untels.securities.*;

@RestController
public class JwtAuthenticationController {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) { throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) { throw new Exception("INVALID_CREDENTIALS", e); }
    }
}
```

---

## 👤 2. JAIR - Gestión de Usuarios y Roles

Responsable del registro, edición y listado de personal y estudiantes.

### 📄 entities/Usuario.java
```java
package pe.edu.untels.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    @Column(nullable = false, unique = true, length = 30) private String username;
    @Column(nullable = false, length = 255) private String password;
    @Column(nullable = false, unique = true, length = 20) private String codigo;
    @Column(length = 20) private String carnet;
    @Column(length = 8) private String dni;
    @Column(nullable = false, length = 100) private String nombre;
    @Column(nullable = false, length = 100) private String email;
    @Column(length = 20) private String telefono;
    @Column(nullable = false, length = 20) private String rol;
    @Column(length = 80) private String carrera;
    private Integer ciclo;
    @Column(nullable = false, length = 20) private String estado;

    // Getters y Setters...
}
```

### 📦 dtos/UsuarioDTO.java
```java
package pe.edu.untels.dtos;

public class UsuarioDTO {
    private int idUsuario;
    private String username;
    private String password;
    private String codigo;
    private String carnet;
    private String dni;
    private String nombre;
    private String email;
    private String telefono;
    private String rol;
    private String carrera;
    private Integer ciclo;
    private String estado;
    // Getters y Setters...
}
```

### 🛠️ servicesimplements/UsuarioServiceImplement.java
```java
package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.Usuario;
import pe.edu.untels.repositories.IUsuarioRepository;
import pe.edu.untels.servicesinterfaces.IUsuarioService;
import java.util.*;

@Service
public class UsuarioServiceImplement implements IUsuarioService {
    @Autowired private IUsuarioRepository usuarioRepository;

    @Override public List<Usuario> list() { return usuarioRepository.findAll(); }
    @Override public Usuario insert(Usuario usuario) { return usuarioRepository.save(usuario); }
    @Override public Optional<Usuario> listId(int id) { return usuarioRepository.findById(id); }
    @Override public void edit(Usuario usuario) { usuarioRepository.save(usuario); }
    @Override public void delete(int id) { usuarioRepository.deleteById(id); }
    @Override public List<Usuario> buscarPorRol(String rol) { return usuarioRepository.findByRol(rol); }
    @Override public boolean existeUsername(String username) { return usuarioRepository.existsByUsername(username); }
}
```

---

## 👤 3. CHRISTOPHER - Catálogo de Libros

Responsable del catálogo de libros y la integración con APIs externas (Open Library).

### 📄 entities/Libro.java
```java
package pe.edu.untels.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLibro;
    @Column(nullable = false, length = 100) private String titulo;
    @Column(nullable = false, length = 100) private String autor;
    @Column(nullable = false, unique = true, length = 20) private String isbn;
    @Column(length = 100) private String editorial;
    private Integer anio;
    @Column(nullable = false, length = 30) private String categoria;
    private int stock;
    private int stockTotal;
    @Column(length = 500) private String descripcion;
    @Column(length = 300) private String recurso;
    // Getters y Setters...
}
```

### 🛠️ servicesimplements/LibroServiceImplement.java (Consumo API)
```java
package pe.edu.untels.servicesimplements;

import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.edu.untels.dtos.LibroApiExternaDTO;
import pe.edu.untels.entities.Libro;
import pe.edu.untels.repositories.ILibroRepository;
import pe.edu.untels.servicesinterfaces.ILibroService;
import java.util.*;

@Service
public class LibroServiceImplement implements ILibroService {
    @Autowired private ILibroRepository libroRepository;

    @Override
    public LibroApiExternaDTO buscarPorIsbnEnApi(String isbn) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";
        String jsonResponse = restTemplate.getForObject(url, String.class);
        // Lógica de mapeo Jackson para obtener título, autor, editorial, etc...
        return new LibroApiExternaDTO(); 
    }
    @Override public Libro insert(Libro libro) { return libroRepository.save(libro); }
    @Override public List<Libro> list() { return libroRepository.findAll(); }
}
```

---

## 👤 4. CURO - Gestión de Préstamos

Responsable del flujo de solicitudes de préstamos, validación de stock y devoluciones.

### 📄 entities/Prestamo.java
```java
package pe.edu.untels.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prestamos")
public class Prestamo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrestamo;
    @ManyToOne @JoinColumn(name = "idLibro", nullable = false) private Libro libro;
    @ManyToOne @JoinColumn(name = "idEstudiante", nullable = false) private Usuario estudiante;
    @Column(nullable = false) private LocalDateTime fecha;
    private LocalDateTime fechaRecojo;
    @Column(nullable = false) private LocalDateTime fechaEntrega;
    private LocalDateTime fechaDevolucion;
    @Column(nullable = false, length = 20) private String estado; // solicitado, vigente, devuelto, vencido
    @Column(length = 30) private String motivo;
    @Column(length = 100) private String curso;
    @Column(length = 500) private String observaciones;
    @Column(length = 30) private String estadoDevolucion;
    // Getters y Setters...
}
```

### 🎮 controllers/PrestamoController.java
```java
package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.PrestamoDTO;
import pe.edu.untels.entities.*;
import pe.edu.untels.servicesinterfaces.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    @Autowired private IPrestamoService prestamoService;
    @Autowired private ILibroService libroService;
    @Autowired private IUsuarioService usuarioService;

    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitar(@RequestBody PrestamoDTO dto) {
        Libro libro = libroService.listId(dto.getIdLibro()).get();
        if (libro.getStock() <= 0) return ResponseEntity.badRequest().body("Sin stock disponible");
        
        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setEstudiante(usuarioService.listId(dto.getIdEstudiante()).get());
        prestamo.setFecha(LocalDateTime.now());
        prestamo.setFechaEntrega(dto.getFechaEntrega());
        prestamo.setEstado("solicitado");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.insert(prestamo));
    }
}
```

---

## 👤 5. NICK - Sanciones y Notificaciones

Responsable de generar multas por retrasos y enviar alertas al estudiante.

### 📄 entities/Sancion.java
```java
package pe.edu.untels.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sanciones")
public class Sancion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSancion;
    @ManyToOne @JoinColumn(name = "idEstudiante", nullable = false) private Usuario estudiante;
    @Column(length = 200) private String motivo;
    private int diasSuspension;
    private double multa;
    @Column(nullable = false, length = 20) private String estado;
    @Column(nullable = false) private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFin;
    // Getters y Setters...
}
```

### 🛠️ servicesimplements/SancionServiceImplement.java
```java
package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.Sancion;
import pe.edu.untels.repositories.ISancionRepository;
import pe.edu.untels.servicesinterfaces.ISancionService;
import java.util.*;

@Service
public class SancionServiceImplement implements ISancionService {
    @Autowired private ISancionRepository sancionRepository;

    @Override public Sancion insert(Sancion sancion) { return sancionRepository.save(sancion); }
    @Override public List<Sancion> buscarPorEstudiante(int idEstudiante) { 
        return sancionRepository.findByEstudianteIdUsuario(idEstudiante); 
    }
}
```

---

## 👤 6. NIPPER - Configuración Global

Responsable de los parámetros de negocio (días de préstamo, multas) y documentación OpenAPI.

### 📄 entities/ConfiguracionBiblioteca.java
```java
package pe.edu.untels.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "configuracion_biblioteca")
public class ConfiguracionBiblioteca {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConfiguracionBiblioteca;
    @Column(nullable = false) private int diasMaxPrestamo;
    @Column(nullable = false) private int limitePrestamos;
    @Column(nullable = false) private double multaPorDia;
    private boolean schedulerActivo;
    private boolean notifEmail;
    private boolean alertaStock;
    private boolean modoMant;
    // Getters y Setters...
}
```

### 📄 securities/OpenApiConfig.java
```java
package pe.edu.untels.securities;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API Biblioteca UNTELS")
                .version("1.0")
                .description("Documentación de los servicios backend para la gestión de biblioteca."));
    }
}
```

---
**Generado para la organización técnica del equipo APIBiblioteca - Sprint 3**
