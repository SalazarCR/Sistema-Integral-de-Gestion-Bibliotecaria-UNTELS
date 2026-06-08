# 🚀 Plan de Trabajo Detallado: Sprint 3 (Backend)

Este documento organiza el desarrollo del backend en tareas específicas por integrante. Cada bloque representa un módulo completo con sus relaciones técnicas.

---

## 👤 1. CESAR - Seguridad y Control de Acceso (JWT)
**Objetivo:** Implementar la muralla de seguridad que protege todos los recursos de la API.

| Tarea | Componente | Descripción de Código y Relaciones |
| :--- | :--- | :--- |
| **T1.1** | `Security Core` | Configurar `WebSecurityConfig.java`. Relaciona los filtros con el `UserDetailsService` de Jair. |
| **T1.2** | `JWT Engine` | Implementar `JwtTokenUtil.java` para la firma y verificación de claims. |
| **T1.3** | `Auth Filter` | Crear `JwtRequestFilter.java` para interceptar cada petición HTTP. |
| **T1.4** | `Endpoints` | Desarrollar `JwtAuthenticationController.java` y DTOs (`JwtRequest`, `JwtResponse`). |

**Código Clave (T1.1):**
```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired private JwtUserDetailsService userDetailsService; // Conexión con Jair
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable()).authorizeHttpRequests(a -> a
            .requestMatchers("/login", "/swagger-ui/**").permitAll()
            .anyRequest().authenticated());
        return http.build();
    }
}
```

---

## 👤 2. JAIR - Módulo de Identidad y Usuarios
**Objetivo:** Gestionar la entidad base del sistema y su integración con la seguridad.

| Tarea | Componente | Descripción de Código y Relaciones |
| :--- | :--- | :--- |
| **T2.1** | `Persistence` | Crear `Usuario.java` e `IUsuarioRepository.java`. Base para préstamos y multas. |
| **T2.2** | `Service Layer`| Implementar `UsuarioService` para CRUD y validación de duplicados. |
| **T2.3** | `Bridge` | Desarrollar `JwtUserDetailsService.java` para conectar Usuarios con el módulo de Cesar. |
| **T2.4** | `API REST` | Crear `UsuarioController.java` y `UsuarioDTO.java` para la exposición de datos. |

**Código Clave (T2.3):**
```java
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired private IUsuarioRepository repo;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario u = repo.findByUsername(username); // Relación con BD
        return new User(u.getUsername(), u.getPassword(), new ArrayList<>());
    }
}
```

---

## 👤 3. CHRISTOPHER - Gestión de Inventario (Libros)
**Objetivo:** Administrar el catálogo de libros y la sincronización con fuentes externas.

| Tarea | Componente | Descripción de Código y Relaciones |
| :--- | :--- | :--- |
| **T3.1** | `Data Model` | Entidad `Libro.java` y repositorio. Esencial para el stock que usa Curo. |
| **T3.2** | `External API`| Implementar lógica en `LibroService` para consumir Open Library API via `RestTemplate`. |
| **T3.3** | `Inventory` | Métodos de control de stock total vs disponible. |
| **T3.4** | `Endpoints` | `LibroController.java` y DTOs para búsqueda y registro por ISBN. |

**Código Clave (T3.2):**
```java
public LibroApiExternaDTO buscarPorIsbnEnApi(String isbn) {
    RestTemplate rt = new RestTemplate();
    String json = rt.getForObject("https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn, String.class);
    // Parseo de metadatos del libro...
    return dto;
}
```

---

## 👤 4. CURO - Lógica de Negocio (Préstamos)
**Objetivo:** Orquestar el flujo de préstamos, devoluciones y validación de reglas.

| Tarea | Componente | Descripción de Código y Relaciones |
| :--- | :--- | :--- |
| **T4.1** | `Transaction` | Entidad `Prestamo.java`. Relación `@ManyToOne` con Usuario (Jair) y Libro (Christopher). |
| **T4.2** | `Business Rules`| `PrestamoService` para validar sanciones (Nick) y límites de biblioteca (Nipper). |
| **T4.3** | `Operations` | Lógica de `devolver()` que actualiza stock y genera multas si hay retraso. |
| **T4.4** | `API REST` | `PrestamoController.java` con endpoints de solicitud y aprobación. |

**Código Clave (T4.2):**
```java
@PostMapping("/solicitar")
public ResponseEntity<?> solicitar(@RequestBody PrestamoDTO dto) {
    if (sancionService.tieneSanciones(dto.getIdEstudiante())) return error(); // Relación Nick
    if (libroService.sinStock(dto.getIdLibro())) return error(); // Relación Christopher
    return ok(prestamoService.save(dto));
}
```

---

## 👤 5. NICK - Cumplimiento y Notificaciones
**Objetivo:** Gestionar el estado de sanciones y la mensajería del sistema.

| Tarea | Componente | Descripción de Código y Relaciones |
| :--- | :--- | :--- |
| **T5.1** | `Sanctions` | Entidad `Sancion.java` y repositorio. Se vincula al Usuario de Jair. |
| **T5.2** | `Messaging` | Entidad `Notificacion.java` para avisos de aprobación/rechazo de Curo. |
| **T5.3** | `Compliance` | `SancionService` para marcar multas como cumplidas y liberar al estudiante. |
| **T5.4** | `Alerts API` | Controladores para que el estudiante vea sus multas y mensajes. |

**Código Clave (T5.1):**
```java
@Entity
public class Sancion {
    @ManyToOne @JoinColumn(name = "idEstudiante") 
    private Usuario estudiante; // Relación directa con el módulo de Jair
    private double multa;
    private String estado;
}
```

---

## 👤 6. NIPPER - Configuración y Documentación
**Objetivo:** Proveer los parámetros de control y la interfaz técnica de la API.

| Tarea | Componente | Descripción de Código y Relaciones |
| :--- | :--- | :--- |
| **T6.1** | `Global Config`| Entidad `ConfiguracionBiblioteca.java`. Define multas y días que usa Curo. |
| **T6.2** | `Admin Service`| Servicio para que el administrador cambie las reglas del negocio en caliente. |
| **T6.3** | `Swagger UI` | Configurar `OpenApiConfig.java` para documentar los endpoints de todos. |
| **T6.4** | `API REST` | `ConfiguracionController.java` para ajustes del sistema. |

**Código Clave (T6.3):**
```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
            .title("API Biblioteca")
            .description("Documentación del trabajo de los 6 integrantes"));
    }
}
```

---

## 🚀 Reparto de Tareas por Comiteo (GitHub)

Para un historial de Git limpio, cada tarea debe ser un commit independiente:

1. `feat(security): T1.1 - T1.4 implementacion de seguridad y jwt` (Cesar)
2. `feat(users): T2.1 - T2.4 crud de usuarios e identidad` (Jair)
3. `feat(books): T3.1 - T3.4 gestion de libros y api externa` (Christopher)
4. `feat(loans): T4.1 - T4.4 flujo transaccional de prestamos` (Curo)
5. `feat(compliance): T5.1 - T5.4 sistema de sanciones y avisos` (Nick)
6. `feat(config): T6.1 - T6.4 parametros y documentacion api` (Nipper)
