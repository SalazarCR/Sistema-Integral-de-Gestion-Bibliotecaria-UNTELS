# 📝 CÓDIGO CON LÓGICA COMENTADA — Role, Token y User

---

## 🔐 1. ROLE.java — ENTIDAD DE ROLES

```java
package pe.edu.untels.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * EXPLICACIÓN: Role es una entidad que representa los roles del sistema
 * 
 * Analogía: Es como un carnet que identifica si eres ESTUDIANTE, BIBLIOTECARIO o ADMINISTRADOR
 * 
 * EN LA BASE DE DATOS se crea una tabla llamada "roles" con:
 * - id_role (Identificador único)
 * - name_role (Nombre del rol)
 * - status_role (¿Está activo?)
 * - created_at (Cuándo se creó)
 */
@Entity                                    // ← IMPORTANTE: "@Entity" le dice a Hibernate: "Esta clase es una tabla en la BD"
@Table(name = "roles")                     // ← Especifica que se llamará "roles" en la BD
public class Role {
    
    // ═══════════════════════════════════════════════════════
    // PARTE 1: ATRIBUTOS (propiedades del rol)
    // ═══════════════════════════════════════════════════════
    
    @Id                                    // ← "@Id" = Este es el identificador único (PRIMARY KEY)
    @GeneratedValue                        // ← Auto-incrementa automáticamente (1, 2, 3, ...)
    (strategy = GenerationType.IDENTITY)   // ← IDENTITY = La BD genera el ID (PostgreSQL SERIAL)
    @Column(name = "id_role")              // ← En la BD se llama "id_role"
    private Integer id;                    // ← Tipo: Integer (número entero)
                                          // EJEMPLO: id = 1, 2, 3
    
    @Column(name = "name_role",           // ← En la BD se llama "name_role"
            unique = true,                // ← UNIQUE = No puede haber dos roles con el mismo nombre
            nullable = false)             // ← NOT NULL = Siempre debe tener un valor
    private String name;                  // ← Tipo: String (texto)
                                          // EJEMPLO: name = "ADMINISTRADOR", "BIBLIOTECARIO", "ESTUDIANTE"
    
    @Column(name = "status_role",         // ← En la BD se llama "status_role"
            nullable = false)             // ← NOT NULL = Siempre debe tener true o false
    private Boolean status = true;        // ← Tipo: Boolean (verdadero/falso)
                                          // EJEMPLO: status = true (el rol está activo)
                                          // POR DEFECTO: true (cuando creas un rol, está activo)
    
    @Column(name = "created_at",          // ← En la BD se llama "created_at"
            nullable = false)             // ← NOT NULL = Siempre debe tener una fecha
    private LocalDateTime createdAt       // ← Tipo: LocalDateTime (fecha y hora)
        = LocalDateTime.now();            // ← POR DEFECTO: La hora actual (2026-05-11 11:55:02)
                                          // EJEMPLO: createdAt = 2026-05-11 10:00:00
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 2: CONSTRUCTORES
    // ═══════════════════════════════════════════════════════
    
    // CONSTRUCTOR 1: Vacío (sin parámetros)
    // PARA QUÉ: Hibernate necesita este constructor para crear objetos desde la BD
    public Role() {
        // Vacío (Hibernate lo usa internamente)
    }
    
    // CONSTRUCTOR 2: Con parámetros
    // PARA QUÉ: Cuando quieres crear un nuevo rol manualmente en el código
    public Role(String name, Boolean status) {
        this.name = name;                 // ← Asigna el nombre del rol
        this.status = status;             // ← Asigna el estado (true/false)
        this.createdAt = LocalDateTime.now(); // ← Pon la fecha/hora actual
    }
    
    // LÓGICA:
    // new Role("ADMINISTRADOR", true)
    // ├─ name = "ADMINISTRADOR"
    // ├─ status = true
    // └─ createdAt = 2026-05-11 11:55:02 (la hora actual)
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 3: GETTERS Y SETTERS
    // ═══════════════════════════════════════════════════════
    
    // GETTER: Obtiene el ID
    // PARA QUÉ: Cuando necesitas saber cuál es el ID del role
    public Integer getId() {
        return id;  // ← Retorna el ID (ejemplo: 1)
    }
    
    // SETTER: Asigna el ID
    // PARA QUÉ: Cuando necesitas cambiar el ID (normalmente no se hace)
    public void setId(Integer id) {
        this.id = id;  // ← "this.id" = el atributo de la clase
                       // ← "id" = el parámetro que recibe el método
    }
    
    // GETTER: Obtiene el nombre del rol
    public String getName() {
        return name;  // ← Retorna "ADMINISTRADOR", "BIBLIOTECARIO", etc
    }
    
    // SETTER: Asigna el nombre del rol
    public void setName(String name) {
        this.name = name;  // ← Cambia el nombre del rol
    }
    
    // GETTER: Obtiene el estado del rol
    public Boolean getStatus() {
        return status;  // ← Retorna true (activo) o false (inactivo)
    }
    
    // SETTER: Asigna el estado del rol
    public void setStatus(Boolean status) {
        this.status = status;  // ← Cambia si el rol está activo o no
    }
    
    // GETTER: Obtiene la fecha de creación
    public LocalDateTime getCreatedAt() {
        return createdAt;  // ← Retorna la fecha/hora de creación
    }
    
    // SETTER: Asigna la fecha de creación
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;  // ← Cambia la fecha de creación
    }
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 4: MÉTODOS ÚTILES
    // ═══════════════════════════════════════════════════════
    
    /**
     * MÉTODO: toString()
     * PARA QUÉ: Cuando imprimes un role, muestra toda su información en texto
     * 
     * EJEMPLO:
     * Role role = new Role("ADMINISTRADOR", true);
     * System.out.println(role);
     * 
     * IMPRIME:
     * Role{id=1, name='ADMINISTRADOR', status=true, createdAt=2026-05-11T11:55:02}
     */
    @Override  // ← Indica que estamos reemplazando un método de la clase padre
    public String toString() {
        // ← Retorna un texto formateado con toda la información del rol
        return "Role{" +
            "id=" + id +                   // ← Incluye el ID
            ", name='" + name + '\'' +     // ← Incluye el nombre
            ", status=" + status +         // ← Incluye el estado
            ", createdAt=" + createdAt +   // ← Incluye la fecha
            '}';
    }
}
```

---

## 🛡️ 2. TOKENBLACKLIST.java — ENTIDAD DE BLACKLIST

```java
package pe.edu.untels.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * EXPLICACIÓN: TokenBlacklist es una tabla donde guardamos TOKENS INVÁLIDOS
 * 
 * LÓGICA:
 * 1. Usuario hace LOGIN → Recibe token = "abc123xyz..."
 * 2. Usuario hace LOGOUT → Token va a TokenBlacklist
 * 3. Hacker intenta usar token viejo "abc123xyz..."
 * 4. Sistema verifica: ¿Está en blacklist? → SÍ → RECHAZA
 * 5. Seguridad: ✓ Protegido
 * 
 * EN LA BASE DE DATOS se crea una tabla llamada "token_blacklist" con:
 * - id_token (Identificador único)
 * - token_value (El JWT completo)
 * - expiration_time (Cuándo expira)
 * - created_at (Cuándo se añadió a blacklist)
 */
@Entity
@Table(name = "token_blacklist")
public class TokenBlacklist {
    
    // ═══════════════════════════════════════════════════════
    // PARTE 1: ATRIBUTOS
    // ═══════════════════════════════════════════════════════
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Integer id;                    // ← ID único para cada registro en blacklist
                                          // EJEMPLO: 1, 2, 3, ...
    
    @Column(name = "token_value",         // ← En la BD se llama "token_value"
            unique = true,                // ← UNIQUE = El token no puede repetirse
            nullable = false)             // ← NOT NULL = Siempre debe tener un token
    private String tokenValue;            // ← El JWT completo que se añadió a blacklist
                                          // EJEMPLO: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIi..."
    
    @Column(name = "expiration_time",     // ← En la BD se llama "expiration_time"
            nullable = false)             // ← NOT NULL = Siempre debe tener una fecha
    private LocalDateTime expirationTime; // ← Cuándo expira el token (24 horas después del login)
                                          // EJEMPLO: 2026-05-12 11:55:02 (mañana a la misma hora)
    
    @Column(name = "created_at",          // ← En la BD se llama "created_at"
            nullable = false)             // ← NOT NULL = Siempre debe tener una fecha
    private LocalDateTime createdAt       // ← Cuándo se añadió a blacklist (cuando hizo LOGOUT)
        = LocalDateTime.now();            // ← POR DEFECTO: La hora actual
                                          // EJEMPLO: 2026-05-11 11:55:02 (cuando hizo logout)
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 2: CONSTRUCTORES
    // ═══════════════════════════════════════════════════════
    
    // CONSTRUCTOR 1: Vacío (para Hibernate)
    public TokenBlacklist() {
    }
    
    // CONSTRUCTOR 2: Con parámetros
    // PARA QUÉ: Cuando quieres crear un nuevo registro de blacklist
    public TokenBlacklist(String tokenValue, LocalDateTime expirationTime) {
        this.tokenValue = tokenValue;     // ← El JWT que se está invalidando
        this.expirationTime = expirationTime;  // ← Cuándo expira (recibimos de la BD)
        this.createdAt = LocalDateTime.now();  // ← La hora de LOGOUT (ahora)
    }
    
    // LÓGICA:
    // Usuario hace LOGOUT → Controlador extrae token "abc123xyz..."
    // → Calcula expirationTime = token.expiration (2026-05-12 11:55:02)
    // → Crea: new TokenBlacklist("abc123xyz...", LocalDateTime.of(2026, 5, 12, 11, 55, 2))
    // → Se guarda en BD
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 3: GETTERS Y SETTERS
    // ═══════════════════════════════════════════════════════
    
    public Integer getId() {
        return id;  // ← Retorna el ID del registro en blacklist
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTokenValue() {
        return tokenValue;  // ← Retorna el JWT que está en blacklist
    }
    
    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;  // ← Asigna un nuevo token a blacklist
    }
    
    public LocalDateTime getExpirationTime() {
        return expirationTime;  // ← Retorna cuándo expira el token
    }
    
    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;  // ← Cambia la fecha de expiración
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;  // ← Retorna cuándo se añadió a blacklist
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 4: MÉTODOS ÚTILES
    // ═══════════════════════════════════════════════════════
    
    /**
     * MÉTODO: isExpired()
     * PARA QUÉ: Verifica si el token en blacklist ya expiró
     * 
     * LÓGICA:
     * - Si la hora actual > fecha de expiración → Ya expiró → Retorna true
     * - Si la hora actual <= fecha de expiración → Aún válido → Retorna false
     * 
     * EJEMPLO:
     * Token expiración: 2026-05-12 11:55:02
     * Hora actual: 2026-05-13 15:30:00 (al día siguiente)
     * isExpired() → true (SÍ expiró)
     * 
     * HORA ACTUAL: 2026-05-12 10:00:00 (el mismo día, antes)
     * isExpired() → false (NO expiró, aún es válido)
     */
    public boolean isExpired() {
        // Compara: ¿La hora actual es DESPUÉS de la fecha de expiración?
        return LocalDateTime.now().isAfter(expirationTime);
        
        // ANÁLISIS:
        // LocalDateTime.now() = Hora actual (ejemplo: 2026-05-13 15:30:00)
        // .isAfter(expirationTime) = ¿Es después de la expiración?
        // expirationTime = 2026-05-12 11:55:02
        // 15:30:00 > 11:55:02 → SÍ es después → true
        // Resultado: El token YA EXPIRÓ
    }
}
```

---

## 👤 3. USER.java — ENTIDAD DE USUARIOS

```java
package pe.edu.untels.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * EXPLICACIÓN: User es una entidad que representa a los usuarios del sistema
 * 
 * TIPOS DE USUARIOS:
 * 1. ADMINISTRADOR: Gestiona todo (usuarios, configuración, reportes)
 * 2. BIBLIOTECARIO: Gestiona libros, préstamos, estudiantes
 * 3. ESTUDIANTE: Solicita préstamos, consulta libros
 * 
 * EN LA BASE DE DATOS se crea una tabla llamada "users" con:
 * - id_user (Identificador único)
 * - username_user (Nombre de usuario para login)
 * - password_user (Contraseña encriptada)
 * - email_user (Correo electrónico)
 * - id_role (FK a tabla roles - cuál es su rol)
 * - status_user (¿Está activo o inactivo?)
 * - created_at y updated_at (Fechas de creación/actualización)
 */
@Entity
@Table(name = "users")
public class User {
    
    // ═══════════════════════════════════════════════════════
    // PARTE 1: ATRIBUTOS
    // ═══════════════════════════════════════════════════════
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;                    // ← ID único del usuario
                                          // EJEMPLO: 1, 2, 3, 4, ...
    
    @Column(name = "username_user",       // ← En la BD se llama "username_user"
            unique = true,                // ← UNIQUE = No puede haber dos usuarios con el mismo username
            nullable = false)             // ← NOT NULL = Siempre debe tener un username
    private String username;              // ← Nombre de usuario para LOGIN
                                          // EJEMPLO: "cesar.salazar", "admin.sistema"
    
    @Column(name = "password_user",       // ← En la BD se llama "password_user"
            nullable = false)             // ← NOT NULL = Siempre debe tener una contraseña
    private String password;              // ← Contraseña del usuario
                                          // EJEMPLO: "12345" (en la BD, debería estar encriptada)
    
    @Column(name = "email_user",          // ← En la BD se llama "email_user"
            unique = true)                // ← UNIQUE = No puede haber dos usuarios con el mismo email
    private String email;                 // ← Correo electrónico del usuario
                                          // EJEMPLO: "cesar@untels.edu.pe"
    
    // RELACIÓN CON ROLE (ManyToOne = Muchos usuarios a UN rol)
    @ManyToOne                            // ← "@ManyToOne" = Muchos usuarios pertenecen a UN rol
    @JoinColumn(name = "id_role",        // ← La columna FK en users se llama "id_role"
                nullable = false)         // ← NOT NULL = Siempre debe tener un rol
    private Role role;                    // ← El rol del usuario (ADMINISTRADOR, BIBLIOTECARIO, ESTUDIANTE)
                                          // EJEMPLO: role = Role(id=1, name="ADMINISTRADOR")
    
    // LÓGICA DE RELACIÓN:
    // Un usuario tiene UN rol
    // Un rol puede tener MUCHOS usuarios
    // 
    // Ejemplo:
    // Usuario cesar.salazar → role = ESTUDIANTE
    // Usuario maria.bibliotecaria → role = BIBLIOTECARIO
    // Usuario admin.sistema → role = ADMINISTRADOR
    // 
    // TODOS los estudiantes comparten el MISMO objeto Role
    // Por eso es ManyToOne (muchos usuarios, un rol)
    
    @Column(name = "status_user",         // ← En la BD se llama "status_user"
            nullable = false)             // ← NOT NULL = Siempre debe ser true o false
    private Boolean status = true;        // ← ¿Está el usuario activo o inactivo?
                                          // true = Puede acceder al sistema
                                          // false = No puede acceder
                                          // POR DEFECTO: true (cuando creo usuario, está activo)
    
    @Column(name = "created_at",          // ← En la BD se llama "created_at"
            nullable = false)             // ← NOT NULL = Siempre debe tener una fecha
    private LocalDateTime createdAt       // ← Cuándo se creó el usuario
        = LocalDateTime.now();            // ← POR DEFECTO: La hora actual
                                          // EJEMPLO: 2026-05-11 11:55:02
    
    @Column(name = "updated_at")          // ← En la BD se llama "updated_at"
    private LocalDateTime updatedAt;      // ← Cuándo se modificó por última vez
                                          // EJEMPLO: 2026-05-11 15:30:00
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 2: CONSTRUCTORES
    // ═══════════════════════════════════════════════════════
    
    // CONSTRUCTOR 1: Vacío (para Hibernate)
    public User() {
    }
    
    // CONSTRUCTOR 2: Con todos los parámetros
    public User(String username, String password, String email, Role role) {
        this.username = username;         // ← Asigna nombre de usuario
        this.password = password;         // ← Asigna contraseña
        this.email = email;               // ← Asigna email
        this.role = role;                 // ← Asigna el rol (IMPORTANTE)
        this.status = true;               // ← Por defecto, está activo
        this.createdAt = LocalDateTime.now(); // ← Anota la fecha/hora de creación
    }
    
    // LÓGICA:
    // Role roleEstudiante = roleRepository.findById(3); // ESTUDIANTE
    // new User("cesar.salazar", "12345", "cesar@untels.edu.pe", roleEstudiante)
    // ├─ username = "cesar.salazar"
    // ├─ password = "12345"
    // ├─ email = "cesar@untels.edu.pe"
    // ├─ role = roleEstudiante (el objeto Role)
    // ├─ status = true (activo)
    // └─ createdAt = 2026-05-11 11:55:02
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 3: GETTERS Y SETTERS
    // ═══════════════════════════════════════════════════════
    
    public Integer getId() {
        return id;  // ← Retorna el ID del usuario
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;  // ← Retorna el nombre de usuario
    }
    
    public void setUsername(String username) {
        this.username = username;  // ← Cambia el nombre de usuario
    }
    
    public String getPassword() {
        return password;  // ← Retorna la contraseña
    }
    
    public void setPassword(String password) {
        this.password = password;  // ← Cambia la contraseña
        // NOTA: En producción, aquí se debería ENCRIPTAR la contraseña
        // NO guardar en texto plano como hacemos ahora
    }
    
    public String getEmail() {
        return email;  // ← Retorna el email del usuario
    }
    
    public void setEmail(String email) {
        this.email = email;  // ← Cambia el email
    }
    
    public Role getRole() {
        return role;  // ← Retorna el objeto Role del usuario
        // IMPORTANTE: Esto retorna el objeto completo, no solo el ID
        // Ejemplo: Role{id=3, name='ESTUDIANTE', status=true}
    }
    
    public void setRole(Role role) {
        this.role = role;  // ← Asigna un nuevo rol al usuario
    }
    
    public Boolean getStatus() {
        return status;  // ← Retorna si el usuario está activo (true) o no (false)
    }
    
    public void setStatus(Boolean status) {
        this.status = status;  // ← Cambia si el usuario está activo o inactivo
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;  // ← Retorna la fecha de creación
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;  // ← Retorna la fecha de última actualización
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;  // ← Actualiza la fecha de modificación
    }
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 4: MÉTODOS ÚTILES
    // ═══════════════════════════════════════════════════════
    
    /**
     * MÉTODO: isActive()
     * PARA QUÉ: Verifica rápidamente si el usuario está activo
     * 
     * RETORNA: true (usuario puede acceder) o false (usuario bloqueado)
     */
    public boolean isActive() {
        // Retorna el estado del usuario
        return status != null && status;  // null-safe: si status es null → false
        
        // ANÁLISIS:
        // Si status = null → false (usuario sin estado definido)
        // Si status = false → false (usuario inactivo)
        // Si status = true → true (usuario activo)
    }
    
    /**
     * MÉTODO: hasRole(String roleName)
     * PARA QUÉ: Verifica si el usuario tiene un rol específico
     * 
     * PARÁMETRO: roleName = Nombre del rol a verificar ("ADMINISTRADOR", "ESTUDIANTE", etc)
     * RETORNA: true (tiene ese rol) o false (tiene otro rol)
     * 
     * EJEMPLO:
     * User user = ... (usuario con rol ESTUDIANTE)
     * user.hasRole("ADMINISTRADOR") → false (no es admin)
     * user.hasRole("ESTUDIANTE") → true (sí es estudiante)
     */
    public boolean hasRole(String roleName) {
        // Verifica:
        // 1. ¿Tiene un rol asignado?
        if (role == null) {
            return false;  // ← No tiene rol → No puede tener ese rol
        }
        
        // 2. ¿El nombre del rol coincide?
        return role.getName().equals(roleName);
        // ← Compara el nombre del rol con el parámetro
        // role.getName() = "ESTUDIANTE"
        // roleName = "ESTUDIANTE"
        // "ESTUDIANTE".equals("ESTUDIANTE") → true
    }
    
    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", role=" + (role != null ? role.getName() : "null") +  // Si role es null, muestra "null"
            ", status=" + status +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
```

---

## 🔐 4. AuthServiceImplement.java — SERVICIO DE AUTENTICACIÓN

```java
package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
import pe.edu.untels.entities.User;
import pe.edu.untels.repositories.IUserRepository;
import java.util.Optional;

/**
 * EXPLICACIÓN: AuthServiceImplement es el SERVICIO que maneja la lógica de autenticación
 * 
 * LÓGICA GENERAL:
 * 1. Recibe username y password del usuario
 * 2. Busca el usuario en la BD
 * 3. Valida que la contraseña sea correcta
 * 4. Verifica que el usuario esté activo
 * 5. Verifica que el rol coincida
 * 6. Si todo es correcto, genera JWT token
 * 7. Retorna la respuesta
 * 
 * EN EL MVC:
 * - Controlador recibe HTTP request
 * - Controlador llama a este Servicio
 * - Este Servicio accede a la BD
 * - Retorna resultado al Controlador
 */
@Service  // ← "@Service" = Esta clase maneja lógica de negocio
public class AuthServiceImplement implements IAuthService {
    
    // ═══════════════════════════════════════════════════════
    // PARTE 1: INYECCIÓN DE DEPENDENCIAS (Repositories)
    // ═══════════════════════════════════════════════════════
    
    @Autowired  // ← "@Autowired" = Spring inyecta automáticamente el repository
    private IUserRepository userRepository;  // ← Para acceder a la tabla users en la BD
    
    @Autowired
    private JwtServiceImplement jwtService;  // ← Para generar tokens JWT
    
    // ¿QUÉ ES INYECCIÓN DE DEPENDENCIAS?
    // En lugar de: IUserRepository userRepository = new UserRepository();
    // Spring lo hace automáticamente: userRepository = instance de UserRepository
    // VENTAJA: No necesitas crear los objetos, Spring lo maneja
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 2: MÉTODO LOGIN (LA LÓGICA PRINCIPAL)
    // ═══════════════════════════════════════════════════════
    
    /**
     * MÉTODO: login(LoginRequestDTO request)
     * 
     * PARÁMETRO: LoginRequestDTO request contiene:
     * {
     *   "username": "cesar.salazar",
     *   "password": "12345",
     *   "role": "ESTUDIANTE"
     * }
     * 
     * RETORNA: LoginResponseDTO con:
     * - success: true/false
     * - message: descripción del resultado
     * - token: JWT si es exitoso
     * - user: información del usuario
     * - role: su rol asignado
     * 
     * FLUJO:
     * 1. Extrae datos del request
     * 2. Busca usuario en BD
     * 3. Valida contraseña
     * 4. Valida estado activo
     * 5. Valida rol
     * 6. Genera tokens
     * 7. Retorna respuesta
     */
    public LoginResponseDTO login(LoginRequestDTO request) {
        
        // ═══════════════════════════════════════════════════════
        // PASO 1: EXTRAE LOS DATOS DEL REQUEST
        // ═══════════════════════════════════════════════════════
        
        String username = request.getUsername();  // ← "cesar.salazar"
        String password = request.getPassword();  // ← "12345"
        String roleStr = request.getRole();       // ← "ESTUDIANTE"
        
        // ANÁLISIS:
        // request es un objeto LoginRequestDTO que vino del Frontend
        // Extraemos los 3 valores que necesitamos para validar
        
        
        // ═══════════════════════════════════════════════════════
        // PASO 2: BUSCA EL USUARIO EN LA BASE DE DATOS
        // ═══════════════════════════════════════════════════════
        
        // SQL equivalente:
        // SELECT * FROM users WHERE username_user = 'cesar.salazar' LIMIT 1;
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        // ¿QUÉ ES Optional?
        // Optional<User> es un contenedor que puede tener:
        // - El usuario encontrado (Optional.of(user))
        // - Nada (Optional.empty())
        
        // LÓGICA:
        // if (userOpt.isPresent()) → El usuario existe en la BD
        // if (!userOpt.isPresent()) → El usuario NO existe
        
        // ═══════════════════════════════════════════════════════
        // PASO 3: VALIDACIÓN 1 — ¿EXISTE EL USUARIO?
        // ═══════════════════════════════════════════════════════
        
        if (!userOpt.isPresent()) {
            // ← El usuario NO está en la BD
            
            // LÓGICA: Si no existe, retorna error
            return new LoginResponseDTO(
                false,  // success = false (login falló)
                "Credenciales inválidas",  // mensaje de error
                null,  // token = null (no generamos token)
                null,  // refreshToken = null
                null,  // user = null
                null   // role = null
            );
            // RESPUESTA AL USUARIO:
            // HTTP 401 Unauthorized
            // { "success": false, "message": "Credenciales inválidas" }
        }
        
        // Si llegamos aquí, el usuario EXISTE
        User user = userOpt.get();  // ← Extrae el usuario del Optional
        
        // Ahora 'user' contiene:
        // {
        //   id: 4,
        //   username: "cesar.salazar",
        //   password: "12345",
        //   email: "2213110208@untels.edu.pe",
        //   role: Role{id=3, name='ESTUDIANTE'},
        //   status: true
        // }
        
        
        // ═══════════════════════════════════════════════════════
        // PASO 4: VALIDACIÓN 2 — ¿CONTRASEÑA CORRECTA?
        // ═══════════════════════════════════════════════════════
        
        if (!user.getPassword().equals(password)) {
            // ← La contraseña es INCORRECTA
            
            // LÓGICA:
            // user.getPassword() = "12345" (del usuario en BD)
            // password = "54321" (del request)
            // "12345".equals("54321") → false → NO coinciden
            
            return new LoginResponseDTO(
                false,
                "Credenciales inválidas",  // El usuario existe pero contraseña mal
                null,
                null,
                null,
                null
            );
            // RESPUESTA: HTTP 401 Unauthorized
        }
        
        // Si llegamos aquí, la contraseña es CORRECTA
        
        
        // ═══════════════════════════════════════════════════════
        // PASO 5: VALIDACIÓN 3 — ¿USUARIO ACTIVO?
        // ═══════════════════════════════════════════════════════
        
        if (!user.getStatus()) {
            // ← El usuario está INACTIVO
            
            // LÓGICA:
            // user.getStatus() = false (usuario desactivado por admin)
            // !false → true → Entra a este bloque
            
            return new LoginResponseDTO(
                false,
                "Usuario inactivo",  // Usuario existe pero está bloqueado
                null,
                null,
                null,
                null
            );
            // RESPUESTA: HTTP 401 Unauthorized
            // El admin desactivó este usuario
        }
        
        // Si llegamos aquí, el usuario está ACTIVO
        
        
        // ═══════════════════════════════════════════════════════
        // PASO 6: VALIDACIÓN 4 — ¿ROL COINCIDE?
        // ═══════════════════════════════════════════════════════
        
        if (!user.getRole().getName().equals(roleStr)) {
            // ← El rol NO coincide
            
            // LÓGICA:
            // user.getRole().getName() = "ESTUDIANTE" (su rol en BD)
            // roleStr = "BIBLIOTECARIO" (lo que dice en el login)
            // "ESTUDIANTE".equals("BIBLIOTECARIO") → false → NO coinciden
            
            return new LoginResponseDTO(
                false,
                "Rol no coincide",  // Usuario existe pero rol incorrecto
                null,
                null,
                null,
                null
            );
            // RESPUESTA: HTTP 401 Unauthorized
            // El usuario es ESTUDIANTE pero intentó entrar como BIBLIOTECARIO
        }
        
        // Si llegamos aquí, TODO ES CORRECTO
        
        
        // ═══════════════════════════════════════════════════════
        // PASO 7: TODO VALIDADO — GENERAR TOKENS
        // ═══════════════════════════════════════════════════════
        
        // GENERAR JWT TOKEN (válido por 24 horas)
        String token = jwtService.generateToken(user);
        // token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiaWF..."
        
        // GENERAR REFRESH TOKEN (válido por 7 días)
        String refreshToken = jwtService.generateRefreshToken(user);
        // refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJz..."
        
        
        // ═══════════════════════════════════════════════════════
        // PASO 8: CREAR INFORMACIÓN DEL USUARIO PARA RESPONDER
        // ═══════════════════════════════════════════════════════
        
        // Crea un objeto con la info del usuario (sin datos sensibles)
        UserInfo userInfo = new UserInfo(
            user.getId(),        // id = 4
            user.getUsername(),  // username = "cesar.salazar"
            user.getEmail()      // email = "2213110208@untels.edu.pe"
        );
        // NOTA: No incluimos password por seguridad
        
        
        // ═══════════════════════════════════════════════════════
        // PASO 9: RETORNAR RESPUESTA EXITOSA
        // ═══════════════════════════════════════════════════════
        
        return new LoginResponseDTO(
            true,  // ← success = true (login exitoso)
            "Login exitoso",  // ← mensaje amigable
            token,  // ← JWT token para futuras solicitudes
            refreshToken,  // ← Token para renovar acceso
            userInfo,  // ← Información del usuario
            user.getRole().getName()  // ← Rol asignado ("ESTUDIANTE")
        );
        
        // RESPUESTA AL USUARIO (Frontend):
        // HTTP 200 OK
        // {
        //   "success": true,
        //   "message": "Login exitoso",
        //   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        //   "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        //   "user": {
        //     "id": 4,
        //     "username": "cesar.salazar",
        //     "email": "2213110208@untels.edu.pe"
        //   },
        //   "role": "ESTUDIANTE"
        // }
        
        // EL FRONTEND AHORA:
        // 1. Guarda el token en SessionStorage
        // 2. Guarda el rol en SessionStorage
        // 3. Redirige al dashboard
        // 4. Para futuras solicitudes, envía token en header:
        //    Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
    }
}
```

---

## 🎮 5. AuthController.java — CONTROLADOR

```java
package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
import pe.edu.untels.dtos.ApiResponseDTO;
import pe.edu.untels.servicesimplements.AuthServiceImplement;

/**
 * EXPLICACIÓN: AuthController es el CONTROLADOR que maneja las solicitudes HTTP
 * 
 * EN EL MVC:
 * - Cliente (Frontend) envía HTTP request a /api/auth/login
 * - Este Controlador recibe la solicitud
 * - Valida el formato
 * - Llama al Servicio
 * - Retorna respuesta HTTP
 */
@RestController  // ← "@RestController" = Este es un controlador REST que retorna JSON
@RequestMapping("/api/auth")  // ← Base URL: /api/auth/login, /api/auth/logout, etc
public class AuthController {
    
    // ═══════════════════════════════════════════════════════
    // PARTE 1: INYECCIÓN DE DEPENDENCIA
    // ═══════════════════════════════════════════════════════
    
    @Autowired  // ← Spring inyecta automáticamente el servicio
    private AuthServiceImplement authService;
    
    
    // ═══════════════════════════════════════════════════════
    // PARTE 2: ENDPOINT POST /api/auth/login
    // ═══════════════════════════════════════════════════════
    
    /**
     * MÉTODO: login(LoginRequestDTO request)
     * 
     * HTTP METHOD: POST
     * URL: /api/auth/login
     * CONTENT-TYPE: application/json
     * 
     * BODY ESPERADO (JSON):
     * {
     *   "username": "cesar.salazar",
     *   "password": "12345",
     *   "role": "ESTUDIANTE"
     * }
     * 
     * RESPUESTA (JSON):
     * HTTP 200 OK:
     * {
     *   "success": true,
     *   "message": "Login exitoso",
     *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     *   "user": { ... }
     * }
     * 
     * O si falla:
     * HTTP 401 Unauthorized:
     * {
     *   "success": false,
     *   "message": "Credenciales inválidas"
     * }
     */
    @PostMapping("/login")  // ← Este método responde a POST /api/auth/login
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        // ← "@RequestBody" = Spring automáticamente deserializa el JSON a LoginRequestDTO
        
        // LÓGICA DEL CONTROLADOR:
        
        // ═══════════════════════════════════════════════════════
        // PASO 1: VALIDACIÓN BÁSICA (antes de llamar al servicio)
        // ═══════════════════════════════════════════════════════
        
        // Verifica: ¿Los datos principales no son nulos?
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            // ← Si username es null o vacío
            
            return ResponseEntity
                .badRequest()  // ← HTTP 400 Bad Request
                .body(new ApiResponseDTO(false, "Username requerido"));
            // RESPUESTA:
            // HTTP 400 Bad Request
            // { "success": false, "message": "Username requerido" }
        }
        
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            // ← Si password es null o vacío
            
            return ResponseEntity
                .badRequest()  // ← HTTP 400 Bad Request
                .body(new ApiResponseDTO(false, "Password requerido"));
        }
        
        if (request.getRole() == null || request.getRole().isEmpty()) {
            // ← Si role es null o vacío
            
            return ResponseEntity
                .badRequest()  // ← HTTP 400 Bad Request
                .body(new ApiResponseDTO(false, "Rol requerido"));
        }
        
        // Si llegamos aquí, los datos básicos son válidos
        
        
        // ═══════════════════════════════════════════════════════
        // PASO 2: LLAMAR AL SERVICIO
        // ═══════════════════════════════════════════════════════
        
        // El Controlador NO valida credenciales, eso lo hace el Servicio
        // El Controlador solo orquesta: recibe → llama servicio → devuelve
        
        LoginResponseDTO response = authService.login(request);
        // ← El Servicio hace toda la lógica (buscar usuario, validar, generar token)
        
        // response contiene:
        // - success: true/false
        // - message
        // - token (si es exitoso)
        // - user
        // - role
        
        
        // ═══════════════════════════════════════════════════════
        // PASO 3: DECIDIR EL CÓDIGO HTTP Y RETORNAR
        // ═══════════════════════════════════════════════════════
        
        if (response.getSuccess()) {
            // ← Si el login fue EXITOSO
            
            return ResponseEntity
                .ok(response);  // ← HTTP 200 OK + retorna el response
            // RESPUESTA:
            // HTTP 200 OK
            // {
            //   "success": true,
            //   "message": "Login exitoso",
            //   "token": "...",
            //   ...
            // }
        } else {
            // ← Si el login FALLÓ
            
            return ResponseEntity
                .status(401)  // ← HTTP 401 Unauthorized
                .body(response);  // ← Retorna el response con el error
            // RESPUESTA:
            // HTTP 401 Unauthorized
            // {
            //   "success": false,
            //   "message": "Credenciales inválidas"
            // }
        }
        
        // ═══════════════════════════════════════════════════════
        // RESUMEN DEL FLUJO:
        // ═══════════════════════════════════════════════════════
        // 1. Frontend envía: POST /api/auth/login { "username": "cesar.salazar", ... }
        // 2. Spring deserializa JSON a LoginRequestDTO
        // 3. Controlador valida: ¿No están vacíos?
        // 4. Si está bien, llama: authService.login(request)
        // 5. Servicio valida credenciales en BD
        // 6. Retorna: { success: true, token: "...", ... } o { success: false, ... }
        // 7. Controlador revisa success
        // 8. Si true → HTTP 200 OK
        //    Si false → HTTP 401 Unauthorized
        // 9. Frontend recibe respuesta
        // 10. Si token, guarda en SessionStorage
        //     Si error, muestra mensaje
    }
}
```

---

## 📊 TABLA RESUMEN: LÓGICA DE ROLES, TOKENS Y USUARIOS

| Componente | Función | Lógica |
|-----------|---------|--------|
| **Role** | Clasificar usuarios | ADMINISTRADOR, BIBLIOTECARIO, ESTUDIANTE |
| **User** | Almacenar credenciales | username, password, email, rol |
| **TokenBlacklist** | Invalidar tokens | Cuando hace logout, token va a blacklist |
| **AuthController** | Recibir solicitud HTTP | Valida formato básico |
| **AuthService** | Validar credenciales | Busca usuario, verifica password, rol, estado |
| **JWT** | Autenticar | Token que se envía en cada solicitud |

---

**Documento completado con comentarios de lógica paso a paso**
**Nivel: Principiante con explicaciones detalladas**

