# 📚 GUÍA COMPLETA: ROLE, TOKEN Y CONFIGURACIÓN — Sprint 2

---

## 📋 TABLA DE CONTENIDOS

1. [Introducción](#introducción)
2. [Conceptos de POO](#conceptos-de-poo)
3. [Patrón MVC (Modelo-Vista-Controlador)](#patrón-mvc)
4. [Estructura de las Entidades](#estructura-de-las-entidades)
5. [Role: Sistema de Roles](#role-sistema-de-roles)
6. [TokenBlacklist: Seguridad](#tokenblacklist-seguridad)
7. [ConfigParametro: Configuración](#configparametro-configuración)
8. [Relaciones Entre Entidades](#relaciones-entre-entidades)
9. [Flujo Completo de Login](#flujo-completo-de-login)
10. [Ejemplos Prácticos](#ejemplos-prácticos)

---

# 🎓 INTRODUCCIÓN

## ¿QUÉ ES LO QUE ESTÁ PASANDO?

Estamos construyendo un **Sistema de Autenticación** para la Biblioteca UNTELS.

**Analogía del mundo real:**
```
▶ Cuando entras a una tienda:
  1. Identificas tu rol (Cliente, Vendedor, Gerente)
  2. Recibes una tarjeta de acceso (Token/JWT)
  3. La tienda guarda configuración (horarios, descuentos, etc)
  4. Si haces algo malo, te prohiben entrar (Blacklist)

▶ En nuestra aplicación es EXACTAMENTE lo mismo, pero en código.
```

---

# 🏗️ CONCEPTOS DE POO (Programación Orientada a Objetos)

## ¿QUÉ ES POO?

POO es una forma de organizar código usando **objetos** que tienen:
- **Atributos** (características)
- **Métodos** (acciones)

### EJEMPLO 1: Un USUARIO es un objeto

```
┌─────────────────────────────────┐
│         Usuario                 │
├─────────────────────────────────┤
│ Atributos (características):    │
│ - id: 1                         │
│ - nombre: "Cesar"              │
│ - email: "cesar@untels.edu.pe" │
│ - rol: "ESTUDIANTE"            │
│                                 │
│ Métodos (acciones):            │
│ - login()                      │
│ - logout()                     │
│ - cambiarContraseña()          │
└─────────────────────────────────┘
```

### EJEMPLO 2: Un ROL es un objeto

```
┌─────────────────────────────────┐
│         Rol                     │
├─────────────────────────────────┤
│ Atributos:                      │
│ - id: 1                         │
│ - nombre: "ADMINISTRADOR"       │
│ - estado: true (activo)         │
│                                 │
│ Métodos:                        │
│ - activar()                    │
│ - desactivar()                 │
│ - verificarPermisos()          │
└─────────────────────────────────┘
```

## CLASES VS OBJETOS

**Clase** = Diseño/Plano (como un plano arquitectónico)
**Objeto** = Instancia real (como una casa construida)

```
// CLASE: Es el molde
public class Role {
    String name;
    Boolean status;
}

// OBJETO 1: Instancia real de la clase
Role roleAdmin = new Role();
roleAdmin.name = "ADMINISTRADOR";
roleAdmin.status = true;

// OBJETO 2: Otra instancia real
Role roleEstudiante = new Role();
roleEstudiante.name = "ESTUDIANTE";
roleEstudiante.status = true;
```

## PRINCIPIOS FUNDAMENTALES DE POO

### 1. ENCAPSULACIÓN
**Concepto:** Proteges los datos internos de un objeto

```
┌─────────────────────────────────┐
│         Usuario                 │
├─────────────────────────────────┤
│ - password: SECRETO (privado)  │  ← No se puede acceder directamente
│ - email: PUBLIC (público)      │  ← Se puede acceder
│                                 │
│ + validarPassword()            │  ← Método para verificar
└─────────────────────────────────┘

// Así NO:
usuario.password = "nueva123";  // ❌ ERROR: No puedes acceder

// Así SÍ:
usuario.cambiarPassword("nuevaPassword");  // ✅ CORRECTO
```

### 2. HERENCIA
**Concepto:** Una clase puede heredar de otra

```
        Persona (clase base)
        ├─ nombre
        ├─ email
        ├─ getInfo()
        │
        ├─────────────┬─────────────┬──────────────┐
        │             │             │              │
    Estudiante    Profesor    Bibliotecario   Administrador
    (extiende)    (extiende)   (extiende)     (extiende)

// Ejemplo:
class Usuario extends Persona {
    // Hereda: nombre, email, getInfo()
    // Añade: username, password, rol
}
```

### 3. POLIMORFISMO
**Concepto:** "Muchas formas" - mismo método, comportamiento diferente

```
interface Acceso {
    void login();
}

// Estudiante login
class Estudiante implements Acceso {
    void login() {
        // Ve solo libros y préstamos
    }
}

// Administrador login
class Admin implements Acceso {
    void login() {
        // Ve todo: usuarios, configuración, reportes
    }
}

// El mismo método "login()" hace cosas diferentes según el rol
```

---

# 🎯 PATRÓN MVC (Modelo-Vista-Controlador)

## ¿QUÉ ES MVC?

MVC es una forma de organizar el código dividiéndolo en 3 capas:

```
┌──────────────────────────────────────────────────────────┐
│                     USUARIO (Navegador)                  │
└──────────────────────────────────────────────────────────┘
                            ↓
                     POST /api/auth/login
                            ↓
        ┌───────────────────────────────────────┐
        │          CONTROLADOR (Controller)     │
        │ ────────────────────────────────────  │
        │ ✓ Recibe la solicitud HTTP            │ 1. Entrada
        │ ✓ Valida formato de datos             │
        │ ✓ Llama al Servicio/Modelo            │
        │ ✓ Retorna respuesta HTTP              │
        └───────────────────────────────────────┘
                            ↓
        ┌───────────────────────────────────────┐
        │   MODELO (Model/Servicio/Lógica)     │
        │ ────────────────────────────────────  │
        │ ✓ Recibe datos del Controlador        │ 2. Procesamiento
        │ ✓ Valida reglas de negocio            │
        │ ✓ Accede a base de datos              │
        │ ✓ Devuelve resultado                  │
        └───────────────────────────────────────┘
                            ↓
        ┌───────────────────────────────────────┐
        │  VISTA (Response/JSON Serializado)    │
        │ ────────────────────────────────────  │
        │ ✓ Convierte datos a JSON/XML          │ 3. Salida
        │ ✓ Retorna al usuario                  │
        └───────────────────────────────────────┘
                            ↓
┌──────────────────────────────────────────────────────────┐
│              USUARIO VE RESULTADO EN NAVEGADOR           │
└──────────────────────────────────────────────────────────┘
```

## EJEMPLO CONCRETO: LOGIN EN NUESTRA APP

```
PASO 1: USUARIO (Vue/Angular/Navegador)
┌─────────────────────────────────┐
│ Ingresa credenciales             │
│ - Username: cesar.salazar        │
│ - Password: 12345               │
│ Click en "Login"                │
└─────────────────────────────────┘
              ↓↓↓
PASO 2: CONTROLADOR (AuthController)
┌─────────────────────────────────┐
│ @PostMapping("/login")           │
│ Recibe: LoginRequestDTO          │
│ Valida que los datos no sean nulos
│ Llama: authService.login(req)   │
└─────────────────────────────────┘
              ↓↓↓
PASO 3: MODELO (AuthServiceImplement)
┌─────────────────────────────────┐
│ Recibe: username, password       │
│ Busca usuario en BD:             │
│ SELECT * FROM users WHERE...    │
│ Verifica password               │
│ Genera JWT token               │
│ Retorna: LoginResponseDTO       │
└─────────────────────────────────┘
              ↓↓↓
PASO 4: VISTA (JSON Response)
┌─────────────────────────────────┐
│ HTTP 200 OK                     │
│ {                               │
│   "success": true,              │
│   "message": "Login exitoso",   │
│   "token": "eyJhbGci...",       │
│   "user": { ... }               │
│ }                               │
└─────────────────────────────────┘
              ↓↓↓
PASO 5: USUARIO
└─ El navegador recibe la respuesta
└─ Guarda el token en SessionStorage
└─ Redirige al dashboard
```

---

# 🗂️ ESTRUCTURA DE LAS ENTIDADES

## ¿QUÉ ES UNA ENTIDAD?

Una **Entidad** es una clase que representa una tabla en la base de datos.

```
Cada atributo de la clase = Una columna en la tabla

Clase JAVA                          Tabla SQL
┌─────────────────────┐             ┌──────────────────┐
│ Role                │             │ roles            │
├─────────────────────┤             ├──────────────────┤
│ - id: Integer       │ ────────→   │ id_role (PK)     │
│ - name: String      │ ────────→   │ name_role (VARCHAR)
│ - status: Boolean   │ ────────→   │ status_role (BOOL)
│ - createdAt: Date   │ ────────→   │ created_at (TS)  │
└─────────────────────┘             └──────────────────┘
```

## ANOTACIONES JPA EXPLICADAS

```java
@Entity                          // ← "Esta es una entidad DB"
@Table(name = "roles")           // ← "Corresponde a tabla 'roles'"
public class Role {
    
    @Id                          // ← "Este es el identificador único (PRIMARY KEY)"
    @GeneratedValue              // ← "Se auto-incrementa automáticamente"
    private Integer id;
    
    @Column(name = "name_role")  // ← "Mapea a columna 'name_role' en BD"
    private String name;         // Ejemplo: "ADMINISTRADOR"
    
    @Column(name = "status_role")
    private Boolean status;      // Ejemplo: true
    
    @Temporal(...)
    private LocalDateTime createdAt; // Fecha de creación automática
}
```

---

# 🔐 ROLE: SISTEMA DE ROLES

## ¿QUÉ ES UN ROL?

Un **Rol** es un nivel de acceso/permisos en el sistema.

```
┌─────────────────────────────────────────────────────────┐
│              SISTEMA DE PERMISOS                        │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  ADMINISTRADOR             BIBLIOTECARIO   ESTUDIANTE   │
│  ├─ Usuarios (CRUD)        ├─ Libros       ├─ Perfil   │
│  ├─ Roles (Ver)            ├─ Préstamos    ├─ Libros   │
│  ├─ Config (CRUD)          ├─ Devoluciones ├─ Solicitar│
│  ├─ Sanciones (Ver)        └─ Estudiantes  └─ Historial
│  └─ Reportes                                            │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

## CÓDIGO DEL ROLE

```java
// 📁 Entity: src/main/java/pe/edu/untels/entities/Role.java

@Entity
@Table(name = "roles")
public class Role {
    
    // ATRIBUTOS (propiedades del rol)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer id;
    
    @Column(name = "name_role", unique = true, nullable = false)
    private String name;  // "ADMINISTRADOR", "BIBLIOTECARIO", "ESTUDIANTE"
    
    @Column(name = "status_role", nullable = false)
    private Boolean status = true;  // ¿Está activo?
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // CONSTRUCTORES
    public Role() {}  // Constructor vacío (requerido por JPA)
    
    public Role(String name, Boolean status) {
        this.name = name;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
    
    // GETTERS Y SETTERS
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    // MÉTODOS útiles
    @Override
    public String toString() {
        return "Role{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", status=" + status +
            ", createdAt=" + createdAt +
            '}';
    }
}
```

## FLOW DEL ROLE

```
BASE DE DATOS (PostgreSQL)
┌──────────────────────────────────────────┐
│ roles                                     │
├──────────────────────────────────────────┤
│ id_role | name_role      | status_role   │
├─────────┼────────────────┼───────────────┤
│ 1       | ADMINISTRADOR  | true          │
│ 2       | BIBLIOTECARIO  | true          │
│ 3       | ESTUDIANTE     | true          │
└──────────────────────────────────────────┘
         ↓ (Hibernate mapea automáticamente)
         ↓
CÓDIGO JAVA
new Role(1, "ADMINISTRADOR", true)
         ↓ (Repository accede a los datos)
         ↓
CONTROLADOR/SERVICIO
authService.validateRole(user.getRole())
```

---

# 🛡️ TOKENBLACKLIST: SEGURIDAD

## ¿POR QUÉ EXISTE TOKEN BLACKLIST?

Imagina que:
```
▶ Usuario 1 (Estudiante) hace LOGIN
  └─ Recibe Token: "abc123xyz"
  
▶ Usuario 1 hace LOGOUT
  └─ El token "abc123xyz" se pone en BLACKLIST
  
▶ Alguien (hacker) intenta usar token viejo "abc123xyz"
  └─ El sistema verifica: ¿Está "abc123xyz" en blacklist?
  └─ ✓ SÍ está → RECHAZA LA SOLICITUD
  └─ Seguridad: ✓ Protegido
```

## CÓDIGO DEL TOKEN BLACKLIST

```java
// 📁 Entity: src/main/java/pe/edu/untels/entities/TokenBlacklist.java

@Entity
@Table(name = "token_blacklist")
public class TokenBlacklist {
    
    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Integer id;
    
    @Column(name = "token_value", unique = true, nullable = false)
    private String tokenValue;  // El JWT completo
    
    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;  // Cuándo expira
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // CONSTRUCTORES
    public TokenBlacklist() {}
    
    public TokenBlacklist(String tokenValue, LocalDateTime expirationTime) {
        this.tokenValue = tokenValue;
        this.expirationTime = expirationTime;
        this.createdAt = LocalDateTime.now();
    }
    
    // GETTERS Y SETTERS
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getTokenValue() { return tokenValue; }
    public void setTokenValue(String tokenValue) { this.tokenValue = tokenValue; }
    
    public LocalDateTime getExpirationTime() { return expirationTime; }
    public void setExpirationTime(LocalDateTime expirationTime) { 
        this.expirationTime = expirationTime; 
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    // MÉTODO: ¿Está expirado?
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
```

## FLOW DEL TOKEN BLACKLIST

```
USUARIO HACE LOGOUT
└─ Envía: POST /api/auth/logout
│           Headers: { Authorization: "Bearer abc123xyz" }
│
└─ CONTROLADOR (AuthController)
    └─ Extrae el token: "abc123xyz"
    └─ Llama: jwtService.addToBlacklist(token)
    │
    └─ SERVICIO (JwtServiceImplement)
        └─ Calcula: expirationTime = token.expiration
        └─ Crea: new TokenBlacklist(token, expirationTime)
        └─ Guarda en BD: INSERT INTO token_blacklist...
        │
        └─ RESPUESTA AL USUARIO
            └─ HTTP 200 OK
            └─ { "message": "Logout exitoso" }


USUARIO INTENTA USAR TOKEN VIEJO
└─ Envía: GET /api/usuarios
│           Headers: { Authorization: "Bearer abc123xyz" }
│
└─ FILTRO DE SEGURIDAD (SecurityFilter)
    └─ Extrae token: "abc123xyz"
    └─ Verifica: ¿Está en blacklist?
    │
    ├─ ✗ SÍ está → RECHAZA (401 Unauthorized)
    │
    └─ ✓ NO está → CONTINÚA (usuario tiene acceso)
```

---

# ⚙️ CONFIGPARAMETRO: CONFIGURACIÓN

## ¿QUÉ ES CONFIG PARAMETRO?

Es una tabla de **configuración del sistema**:

```
┌─────────────────────────────────────┐
│   CONFIGURACIÓN DEL SISTEMA         │
├─────────────────────────────────────┤
│ - Días máximos de préstamo: 14      │
│ - Límite de préstamos por estudiante│
│ - Multa por día de retraso: 5.00    │
│ - ¿Scheduler automático activo?     │
│ - ¿Notificaciones por email?        │
│ - ¿Alertas de stock bajo?           │
│ - ¿Modo mantenimiento?              │
└─────────────────────────────────────┘

SOLO EL ADMINISTRADOR puede modificar esto.
```

## CÓDIGO DEL CONFIG PARAMETRO

```java
// 📁 Entity: src/main/java/pe/edu/untels/entities/ConfigParametro.java

@Entity
@Table(name = "config_parametros")
public class ConfigParametro {
    
    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_config")
    private Integer id;
    
    @Column(name = "key_config", unique = true, nullable = false)
    private String key;  // "dias_max_prestamo", "multa_por_dia", etc
    
    @Column(name = "value_config", nullable = false)
    private String value;  // "14", "5.00", "true", etc
    
    @Column(name = "type_config")
    private String type;  // "INTEGER", "DECIMAL", "BOOLEAN"
    
    @Column(name = "status_config", nullable = false)
    private Boolean status = true;  // ¿Está activa esta config?
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // CONSTRUCTORES
    public ConfigParametro() {}
    
    public ConfigParametro(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
        this.status = true;
        this.createdAt = LocalDateTime.now();
    }
    
    // GETTERS Y SETTERS
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    
    public String getValue() { return value; }
    public void setValue(String value) { 
        this.value = value;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    // MÉTODOS ÚTILES
    
    // Obtener el valor como Integer
    public Integer getValueAsInteger() {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    // Obtener el valor como Decimal
    public Double getValueAsDouble() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    // Obtener el valor como Boolean
    public Boolean getValueAsBoolean() {
        return Boolean.parseBoolean(value);
    }
}
```

## CONFIGURACIONES INICIALES

```sql
-- Insertadas en la BD:

-- Días máximos de préstamo
INSERT INTO config_parametros (key_config, value_config, type_config)
VALUES ('dias_max_prestamo', '14', 'INTEGER');

-- Límite de préstamos por estudiante
INSERT INTO config_parametros (key_config, value_config, type_config)
VALUES ('limite_prestamos_por_estudiante', '3', 'INTEGER');

-- Multa por día de retraso
INSERT INTO config_parametros (key_config, value_config, type_config)
VALUES ('multa_por_dia_retraso', '5.00', 'DECIMAL');

-- ¿Scheduler automático activo?
INSERT INTO config_parametros (key_config, value_config, type_config)
VALUES ('scheduler_activo', 'true', 'BOOLEAN');

-- ¿Notificaciones por email?
INSERT INTO config_parametros (key_config, value_config, type_config)
VALUES ('notificaciones_email', 'true', 'BOOLEAN');
```

## FLOW DEL CONFIG PARAMETRO

```
ADMINISTRADOR QUIERE CAMBIAR CONFIGURACIÓN
│
└─ Solicitud: PUT /api/config/dias_max_prestamo
│              Body: { "value": "21" }
│
└─ CONTROLADOR (ConfigParametroController)
    └─ Verifica: ¿Usuario es ADMINISTRADOR?
    │   └─ ✗ NO → ERROR 403 FORBIDDEN
    │   └─ ✓ SÍ → Continúa
    │
    └─ Llama: configService.actualizarParametro("dias_max_prestamo", "21")
    │
    └─ SERVICIO (ConfigParametroServiceImplement)
        └─ Busca parámetro en BD: SELECT * FROM config_parametros WHERE key_config='dias_max_prestamo'
        └─ Valida: ¿Es "21" un número válido?
        │   └─ ✗ NO → ERROR 400 BAD REQUEST
        │   └─ ✓ SÍ → Continúa
        │
        └─ Actualiza: UPDATE config_parametros SET value_config='21' WHERE key_config='dias_max_prestamo'
        │
        └─ Respuesta: HTTP 200 OK
            └─ { "success": true, "message": "Configuración actualizada" }
```

---

# 🔗 RELACIONES ENTRE ENTIDADES

## DIAGRAMA DE RELACIONES

```
┌──────────────┐         ┌──────────────┐         ┌──────────────────┐
│    Role      │◄────┐   │    User      │         │ TokenBlacklist   │
│──────────────│     │   │──────────────│         │──────────────────│
│ id_role (PK) │     │   │ id_user (PK) │         │ id_token (PK)    │
│ name_role    │     └───┼─ id_role (FK)│         │ token_value      │
│ status_role  │         │ username     │         │ expiration_time  │
│ created_at   │         │ password     │         │ created_at       │
└──────────────┘         │ email        │         └──────────────────┘
                         │ status_user  │
                         │ created_at   │
                         │ updated_at   │
                         └──────────────┘
                                ▲
                                │
                    ┌───────────┴────────────┐
                    │                        │
              ┌─────────┐         ┌──────────────────┐
              │Student  │         │ ConfigParametro  │
              │(futuro) │         │──────────────────│
              │         │         │ id_config (PK)   │
              └─────────┘         │ key_config       │
                                  │ value_config     │
                                  │ type_config      │
                                  │ status_config    │
                                  │ created_at       │
                                  │ updated_at       │
                                  └──────────────────┘

RELACIONES:
- Role ────→ User        (1 Role tiene muchos Users)
- User ────→ TokenBlacklist (1 User puede tener muchos tokens en blacklist)
- ConfigParametro ─────→ Admin (Solo Admin accede)
```

## RELACIÓN: ROLE × USER (1 a MUCHOS)

```
UNA ENTIDAD DE ROLE PUEDE TENER MUCHOS USUARIOS

Role: ADMINISTRADOR (id=1)
├─ User: admin.sistema
└─ (Solo 1 admin en el sistema)

Role: BIBLIOTECARIO (id=2)
├─ User: juan.bibliotecario
└─ User: maria.bibliotecaria

Role: ESTUDIANTE (id=3)
├─ User: cesar.salazar
├─ User: christopher.risco
└─ User: curo.valenzuela


EN CÓDIGO JAVA:

// En la entidad User:
@ManyToOne
@JoinColumn(name = "id_role", nullable = false)
private Role role;

// Significa: "Muchos Users pertenecen a UN Role"
```

## RELACIÓN: USER × TOKENBLACKLIST (1 a MUCHOS)

```
UN USUARIO PUEDE TENER MULTIPLES TOKENS EN BLACKLIST

User: cesar.salazar
├─ Token 1: abc123xyz... (logout a las 10:00)
├─ Token 2: def456uvw... (logout a las 10:30)
└─ Token 3: ghi789tst... (logout a las 11:00)

En la BD:
┌──────────┬──────────────────────────────────────────┐
│ id_token  │ token_value                              │
├───────────┼──────────────────────────────────────────┤
│ 1         │ abc123xyz...                             │
│ 2         │ def456uvw...                             │
│ 3         │ ghi789tst...                             │
└───────────┴──────────────────────────────────────────┘

Técnicamente, Token blacklist no tiene foreign key a User.
Pero lógicamente, pertenece a ese usuario.
```

---

# 🔄 FLUJO COMPLETO DE LOGIN

## PASO A PASO: QUÉ SUCEDE EN LOGIN

```
╔════════════════════════════════════════════════════════════════════╗
║ PASO 1: USUARIO INGRESA CREDENCIALES (Frontend)                   ║
╚════════════════════════════════════════════════════════════════════╝

Usuario abre navegador y ve:
┌──────────────────────────────────┐
│  LOGIN - Biblioteca UNTELS       │
│                                  │
│ Username: [cesar.salazar     ]  │
│ Password: [     ***         ]  │
│ Rol:      [ESTUDIANTE       ▼] │
│                                  │
│          [ Login ]  [ Cancelar] │
└──────────────────────────────────┘

Usuario hace click en "Login"
El navegador MANDA HTTP REQUEST:

POST /api/auth/login
Content-Type: application/json

{
  "username": "cesar.salazar",
  "password": "12345",
  "role": "ESTUDIANTE"
}


╔════════════════════════════════════════════════════════════════════╗
║ PASO 2: CONTROLADOR RECIBE LA SOLICITUD                           ║
╚════════════════════════════════════════════════════════════════════╝

ARCHIVO: AuthController.java

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
    
    // request contiene:
    // - username: "cesar.salazar"
    // - password: "12345"
    // - role: "ESTUDIANTE"
    
    // VALIDACIÓN 1: ¿Los datos no son nulos?
    if (request.getUsername() == null || 
        request.getPassword() == null) {
        return ResponseEntity
            .badRequest()
            .body(new ApiResponseDTO(false, "Datos incompletos"));
    }
    
    // VALIDACIÓN 2: ¿El rol es válido?
    if (!isValidRole(request.getRole())) {
        return ResponseEntity
            .badRequest()
            .body(new ApiResponseDTO(false, "Rol inválido"));
    }
    
    // Si llegó aquí, los datos son válidos
    // Ahora llama al SERVICIO
    LoginResponseDTO response = authService.login(request);
    
    if (response.getSuccess()) {
        return ResponseEntity.ok(response);  // HTTP 200
    } else {
        return ResponseEntity
            .status(401)  // HTTP 401 Unauthorized
            .body(response);
    }
}


╔════════════════════════════════════════════════════════════════════╗
║ PASO 3: SERVICIO VALIDA CREDENCIALES                              ║
╚════════════════════════════════════════════════════════════════════╝

ARCHIVO: AuthServiceImplement.java

public LoginResponseDTO login(LoginRequestDTO request) {
    
    String username = request.getUsername();  // "cesar.salazar"
    String password = request.getPassword();  // "12345"
    String roleStr = request.getRole();       // "ESTUDIANTE"
    
    // PASO 3A: Buscar usuario en BD
    // SELECT * FROM users WHERE username_user = 'cesar.salazar'
    Optional<User> userOpt = userRepository.findByUsername(username);
    
    if (!userOpt.isPresent()) {
        // Usuario NO existe en BD
        return new LoginResponseDTO(
            false,
            "Credenciales inválidas",
            null,
            null,
            new UserInfo(),
            null
        );  // HTTP 401
    }
    
    User user = userOpt.get();
    // user = { id: 4, username: "cesar.salazar", password: "12345", role: ESTUDIANTE, ... }
    
    // PASO 3B: Verificar contraseña
    if (!user.getPassword().equals(password)) {
        // Contraseña incorrecta
        return new LoginResponseDTO(false, "Credenciales inválidas", null, null, new UserInfo(), null);
    }
    
    // PASO 3C: Verificar que usuario está ACTIVO
    if (!user.getStatus()) {
        // Usuario desactivado
        return new LoginResponseDTO(false, "Usuario inactivo", null, null, new UserInfo(), null);
    }
    
    // PASO 3D: Verificar que el ROL coincida
    if (!user.getRole().getName().equals(roleStr)) {
        // El usuario dice: "Soy ESTUDIANTE"
        // Pero en BD está: BIBLIOTECARIO
        return new LoginResponseDTO(false, "Rol no coincide", null, null, new UserInfo(), null);
    }
    
    // ✓ TODO CORRECTO - Generar tokens
    
    // PASO 3E: Generar JWT Token
    String token = jwtService.generateToken(user);
    // token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiaWF..." + etc
    
    // PASO 3F: Generar Refresh Token
    String refreshToken = jwtService.generateRefreshToken(user);
    
    // PASO 3G: Crear información del usuario
    UserInfo userInfo = new UserInfo(
        user.getId(),
        user.getUsername(),
        user.getEmail()
    );
    
    // PASO 3H: Retornar respuesta exitosa
    return new LoginResponseDTO(
        true,  // success: true
        "Login exitoso",
        token,
        refreshToken,
        userInfo,
        user.getRole().getName()  // "ESTUDIANTE"
    );
}


╔════════════════════════════════════════════════════════════════════╗
║ PASO 4: CONTROLADOR RETORNA RESPUESTA                             ║
╚════════════════════════════════════════════════════════════════════╝

El Controlador convierte el objeto LoginResponseDTO a JSON:

HTTP 200 OK
Content-Type: application/json

{
  "success": true,
  "message": "Login exitoso",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiaWF...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJz...",
  "user": {
    "id": 4,
    "username": "cesar.salazar",
    "email": "2213110208@untels.edu.pe"
  },
  "role": "ESTUDIANTE"
}


╔════════════════════════════════════════════════════════════════════╗
║ PASO 5: FRONTEND RECIBE RESPUESTA                                 ║
╚════════════════════════════════════════════════════════════════════╝

El navegador recibe la respuesta:

// En JavaScript/Vue/Angular
if (response.success) {
    // Guardar token en SessionStorage
    sessionStorage.setItem('token', response.token);
    sessionStorage.setItem('role', response.role);
    sessionStorage.setItem('user_id', response.user.id);
    
    // Redirigir al dashboard
    router.push('/dashboard');
    
} else {
    // Mostrar error
    alert(response.message);
}


╔════════════════════════════════════════════════════════════════════╗
║ PASO 6: USUARIO ACCEDE AL SISTEMA                                 ║
╚════════════════════════════════════════════════════════════════════╝

Ahora el usuario:
✓ Ve su usuario: cesar.salazar
✓ Ve su rol: ESTUDIANTE
✓ Accede solo a las opciones de estudiante
✓ Puede consultar libros, solicitar préstamos, etc

Si intenta acceder a "/api/admin/usuarios":
  └─ Sistema valida token
  └─ Verifica rol: "Necesitas ser ADMINISTRADOR"
  └─ RECHAZA: "No tienes permisos"
```

---

# 💡 EJEMPLOS PRÁCTICOS

## EJEMPLO 1: CREAR UN NUEVO USUARIO

```java
// 1. Frontend emite POST a crear usuario
POST /api/users
Body: {
  "username": "nuevo.usuario",
  "password": "pass123",
  "email": "nuevo@untels.edu.pe",
  "roleId": 3  // ESTUDIANTE
}

// 2. Controlador recibe (UserController)
@PostMapping
public ResponseEntity<?> crearUsuario(@RequestBody UserInsertDTO dto) {
    // Valida datos
    // Llama: userService.crear(dto)
}

// 3. Servicio crea el objeto
User nuevoUsuario = new User();
nuevoUsuario.setUsername("nuevo.usuario");
nuevoUsuario.setPassword("pass123");
nuevoUsuario.setEmail("nuevo@untels.edu.pe");

Role role = roleRepository.findById(3);  // ESTUDIANTE
nuevoUsuario.setRole(role);
nuevoUsuario.setStatus(true);

// 4. Guarda en BD
userRepository.save(nuevoUsuario);

// 5. Retorna al frontend
return UserDTO.fromEntity(nuevoUsuario);
```

## EJEMPLO 2: USUARIO HACE LOGOUT

```java
// 1. Frontend emite POST logout con token
POST /api/auth/logout
Headers: {
  "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...."
}

// 2. Controlador extrae token
@PostMapping("/logout")
public ResponseEntity<?> logout(
    @RequestHeader("Authorization") String authHeader) {
    
    // Extrae token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...."
    String token = authHeader.replace("Bearer ", "");
    
    // Llama: jwtService.logout(token)
}

// 3. Servicio JWT añade a blacklist
public void logout(String token) {
    // Extrae fecha de expiración del token
    LocalDateTime expiration = jwtService.getExpirationDate(token);
    
    // Crea registro en blacklist
    TokenBlacklist blacklist = new TokenBlacklist(token, expiration);
    
    // Guarda: INSERT INTO token_blacklist...
    tokenBlacklistRepository.save(blacklist);
}

// 4. Próximas solicitudes con este token
// Sistema verifica: ¿Está en blacklist?
// └─ ✓ SÍ → RECHAZA (401 Unauthorized)
```

## EJEMPLO 3: ADMINISTRADOR CAMBIA CONFIGURACIÓN

```java
// 1. Admin abre panel de configuración
// Ve: "Días máximos de préstamo: 14"
// Cambia a: "21"

PUT /api/config/dias_max_prestamo
Body: { "value": "21" }
Headers: { "Authorization": "Bearer ADMIN_TOKEN" }

// 2. Controlador valida permisos
@PutMapping("/{key}")
public ResponseEntity<?> actualizar(@PathVariable String key, @RequestBody ConfigDTO dto) {
    
    // Verifica: ¿Usuario actual es ADMINISTRADOR?
    User user = jwtService.getUserFromToken();
    if (!user.getRole().getName().equals("ADMINISTRADOR")) {
        return ResponseEntity
            .status(403)  // FORBIDDEN
            .body(new ApiResponseDTO(false, "No tienes permisos"));
    }
    
    // Llama: configService.actualizar(key, dto.getValue())
}

// 3. Servicio valida y actualiza
public void actualizar(String key, String value) {
    // Busca: SELECT * FROM config_parametros WHERE key_config='dias_max_prestamo'
    ConfigParametro config = configRepository.findByKey(key);
    
    if (config == null) {
        throw new ConfigNotFoundException("Parámetro no encontrado");
    }
    
    // Valida el tipo
    if (config.getType().equals("INTEGER")) {
        try {
            Integer.parseInt(value);  // ¿Es un número?
        } catch (Exception e) {
            throw new ValidationException("Debe ser un número entero");
        }
    }
    
    // Actualiza: UPDATE config_parametros SET value_config='21' WHERE key_config='dias_max_prestamo'
    config.setValue(value);
    configRepository.save(config);
}

// 4. Ahora el sistema usa la nueva configuración
// Cuando se cree un préstamo: duracion = 21 días (antes era 14)
```

---

## 📊 RESUMEN: RELACIÓN DE CLASES

```
USER (Usuario)
├─ Tiene UN: Role (Rol)
├─ Muchos: TokenBlacklist (Tokens invalidados al logout)
└─ Accede a: ConfigParametro (Solo Admin)

ROLE (Rol)
├─ Tiene MUCHOS: Users (Los usuarios de este rol)
└─ Tipos: ADMINISTRADOR, BIBLIOTECARIO, ESTUDIANTE

TOKENBLACKLIST (Token invalidado)
├─ Guarda: JWT Token de usuario que hizo logout
└─ Valida: Rechaza tokens en este acceso

CONFIGPARAMETRO (Configuración)
├─ Parámetros del sistema
├─ Solo modificables por ADMINISTRADOR
└─ Ejemplos: días_max_prestamo, multa_por_dia, etc
```

---

## 🎯 CONCEPTOS CLAVE RECORDAR

```
1. ENTIDAD = Tabla en BD + Clase en Java
   Rol → tabla roles + clase Role.java

2. ATRIBUTO = Columna en BD
   name_role → nombre del rol

3. RELACIÓN = Cómo se conectan entidades
   User tiene 1 Role (muchos usuarios, 1 rol cada uno)

4. ANOTACIÓN = Instrucción para Hibernate
   @Entity dice: "Esto va a la BD"
   @Id dice: "Este es el identificador único"

5. MVC = Separación de responsabilidades
   Controlador: Recibe HTTP request
   Modelo/Servicio: Procesa lógica
   Vista: Retorna respuesta

6. POO = Organize código en objetos
   Cada objeto tiene atributos y métodos
   Los objetos se relacionan entre sí

7. SEGURIDAD = Proteger el acceso
   Role: Qué puede hacer
   TokenBlacklist: Evita reutilización de tokens
   Encapsulación: Proteger datos sensibles
```

---

## 🔗 FLUJOS RESUMIDOS

```
LOGIN:
Usuario → POST /login → Controlador → Servicio → BD
        ← JSON con token ← Generador JWT ← Validación

LOGOUT:
Usuario → POST /logout → Controlador → Servicio → BD
        ← Confirmación ← Añade a blacklist

SOLICITAR ACCESO:
Usuario → GET /recurso → Filtro de seguridad
                         ├─ ¿Token válido?
                         ├─ ¿Rol tiene permisos?
                         ├─ ¿Rol activo?
                         └─ ✓ Permite → Acceso
                            ✗ Rechaza → 401/403

CAMBIAR CONFIGURACIÓN (Admin):
Admin → PUT /config/{key} → Controlador
                            ├─ ¿Es Admin?
                            └─ ✓ Sí → Servicio → Actualiza
                               ✗ No → ERROR 403
```

---

## ✅ CONCLUSIÓN

```
Ahora entiendes:

✓ Role: Cómo se clasifican usuarios (ADMIN, BIBLIO, ESTUDIANTE)
✓ TokenBlacklist: Cómo se protege el logout
✓ ConfigParametro: Cómo se personalizan reglas del sistema
✓ POO: Cómo se organizan datos en objetos
✓ MVC: Cómo fluye la solicitud (Controlador → Servicio → Vista)
✓ Relaciones: Cómo se conectan las entidades en la BD
✓ Seguridad: Cómo se validan permisos

El próximo paso: Implementar HUF07.2 (Validación de roles)
```

---

**Documento completado: 11/05/2026**
**Autor: GitHub Copilot**
**Nivel: Principiante a Intermedio**

