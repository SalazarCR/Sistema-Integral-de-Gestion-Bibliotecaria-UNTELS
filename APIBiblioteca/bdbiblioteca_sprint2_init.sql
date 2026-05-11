-- ========================================================================================================
-- SCRIPT SQL: INICIALIZACIÓN BASE DE DATOS SPRINT 2
-- Proyecto: APIBiblioteca
-- Fecha: 11/05/2026
-- Descripción: Crea tablas de autenticación y datos iniciales para Sprint 2 (CESAR - HUF07)
-- ========================================================================================================

-- ========================================================================================================
-- 1. CREAR TABLA: ROLES
-- ========================================================================================================
CREATE TABLE IF NOT EXISTS roles (
    id_role SERIAL PRIMARY KEY,
    name_role VARCHAR(50) UNIQUE NOT NULL,
    status_role BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Comentario: Esta tabla contiene los 3 roles del sistema:
-- - ADMINISTRADOR (único en el sistema, gestiona configuración)
-- - BIBLIOTECARIO (gestiona libros, préstamos, estudiantes)
-- - ESTUDIANTE (solicita préstamos, consulta catálogo)

-- ========================================================================================================
-- 2. CREAR TABLA: USERS
-- ========================================================================================================
CREATE TABLE IF NOT EXISTS users (
    id_user SERIAL PRIMARY KEY,
    username_user VARCHAR(100) UNIQUE NOT NULL,
    password_user VARCHAR(255) NOT NULL,
    email_user VARCHAR(100) UNIQUE,
    id_role INTEGER NOT NULL REFERENCES roles(id_role),
    status_user BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comentario: Tabla principal de usuarios del sistema
-- Se relaciona con roles mediante id_role (Foreign Key)
-- Contiene ADMINISTRADOR, BIBLIOTECARIO y ESTUDIANTE

-- ========================================================================================================
-- 3. CREAR TABLA: TOKEN_BLACKLIST
-- ========================================================================================================
CREATE TABLE IF NOT EXISTS token_blacklist (
    id_token SERIAL PRIMARY KEY,
    token_value TEXT UNIQUE NOT NULL,
    expiration_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Comentario: Tabla de seguridad que almacena JWT tokens invalidados
-- Cuando un usuario hace LOGOUT, su token va aqui para evitar reutilización
-- Se limpia automáticamente cuando los tokens expiran

-- ========================================================================================================
-- 4. CREAR ÍNDICES PARA PERFORMANCE
-- ========================================================================================================
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username_user);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email_user);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(id_role);
CREATE INDEX IF NOT EXISTS idx_tokenblacklist_expiration ON token_blacklist(expiration_time);

-- ========================================================================================================
-- 5. LIMPIAR DATOS PREVIOS (OPCIONAL - comentar si quieres conservar)
-- ========================================================================================================
-- DELETE FROM token_blacklist;
-- DELETE FROM users;
-- DELETE FROM roles;

-- ========================================================================================================
-- 6. INSERTAR ROLES (3 roles disponibles)
-- ========================================================================================================
INSERT INTO roles (name_role, status_role) VALUES
('ADMINISTRADOR', true),
('BIBLIOTECARIO', true),
('ESTUDIANTE', true)
ON CONFLICT (name_role) DO NOTHING;  -- No duplica si ya existen

-- Verificación (ejecutar para ver):
-- SELECT * FROM roles;

-- ========================================================================================================
-- 7. INSERTAR USUARIOS DE EJEMPLO
-- ========================================================================================================

-- ========================================================================================================
-- USUARIO 1: ADMINISTRADOR (ÚNICO EN EL SISTEMA)
-- ========================================================================================================
INSERT INTO users (username_user, password_user, email_user, id_role, status_user) VALUES
(
  'admin.sistema',              -- Username único
  '12345',                       -- Password (sin encriptar - cambiar en producción)
  'admin@untels.edu.pe',         -- Email
  1,                             -- id_role = 1 (ADMINISTRADOR)
  true                           -- status_user = activo
)
ON CONFLICT (username_user) DO NOTHING;

-- Descripción del usuario:
-- Rol: ADMINISTRADOR
-- Tabla: users
-- Responsabilidades: Gestionar usuarios, configurar sistema (HUF01, HUF02)
-- Status: ACTIVO

-- ========================================================================================================
-- USUARIO 2: BIBLIOTECARIO (ejemplo 1)
-- ========================================================================================================
INSERT INTO users (username_user, password_user, email_user, id_role, status_user) VALUES
(
  'juan.bibliotecario',          -- Username único
  '12345',                       -- Password
  'juan.gonzales@untels.edu.pe', -- Email
  2,                             -- id_role = 2 (BIBLIOTECARIO)
  true                           -- status_user = activo
)
ON CONFLICT (username_user) DO NOTHING;

-- Descripción del usuario:
-- Rol: BIBLIOTECARIO
-- Tabla: users
-- Responsabilidades: Gestionar libros, préstamos, estudiantes (HUF03, HUF04, HUF05)
-- Status: ACTIVO

-- ========================================================================================================
-- USUARIO 3: BIBLIOTECARIO (ejemplo 2)
-- ========================================================================================================
INSERT INTO users (username_user, password_user, email_user, id_role, status_user) VALUES
(
  'maria.bibliotecaria',         -- Username único
  '12345',                       -- Password
  'maria.lopez@untels.edu.pe',   -- Email
  2,                             -- id_role = 2 (BIBLIOTECARIO)
  true                           -- status_user = activo
)
ON CONFLICT (username_user) DO NOTHING;

-- Descripción del usuario:
-- Rol: BIBLIOTECARIO
-- Tabla: users
-- Status: ACTIVO

-- ========================================================================================================
-- USUARIO 4: ESTUDIANTE (ejemplo 1 - CESAR)
-- ========================================================================================================
INSERT INTO users (username_user, password_user, email_user, id_role, status_user) VALUES
(
  'cesar.salazar',               -- Username único
  '12345',                       -- Password
  '2213110208@untels.edu.pe',    -- Email del estudiante Cesar
  3,                             -- id_role = 3 (ESTUDIANTE)
  true                           -- status_user = activo
)
ON CONFLICT (username_user) DO NOTHING;

-- Descripción del usuario:
-- Rol: ESTUDIANTE
-- Tabla: users
-- Responsabilidades: Solicitar préstamos, consultar libros (HUF08, HUF09, HUF10)
-- Status: ACTIVO

-- ========================================================================================================
-- USUARIO 5: ESTUDIANTE (ejemplo 2 - CHRISTOPHER)
-- ========================================================================================================
INSERT INTO users (username_user, password_user, email_user, id_role, status_user) VALUES
(
  'christopher.risco',           -- Username único
  '12345',                       -- Password
  '2213010394@untels.edu.pe',    -- Email del estudiante Christopher
  3,                             -- id_role = 3 (ESTUDIANTE)
  true                           -- status_user = activo
)
ON CONFLICT (username_user) DO NOTHING;

-- Descripción del usuario:
-- Rol: ESTUDIANTE
-- Tabla: users
-- Status: ACTIVO

-- ========================================================================================================
-- USUARIO 6: ESTUDIANTE (ejemplo 3 - CURO)
-- ========================================================================================================
INSERT INTO users (username_user, password_user, email_user, id_role, status_user) VALUES
(
  'curo.valenzuela',             -- Username único
  '12345',                       -- Password
  'curo.valenzuela@untels.edu.pe', -- Email del estudiante Curo
  3,                             -- id_role = 3 (ESTUDIANTE)
  true                           -- status_user = activo
)
ON CONFLICT (username_user) DO NOTHING;

-- Descripción del usuario:
-- Rol: ESTUDIANTE
-- Tabla: users
-- Status: ACTIVO

-- ========================================================================================================
-- 8. CONSULTAS DE VERIFICACIÓN
-- ========================================================================================================

-- Verificar ROLES creados:
-- SELECT * FROM roles;

-- Verificar USUARIOS creados:
-- SELECT
--   u.id_user,
--   u.username_user,
--   u.email_user,
--   r.name_role,
--   u.status_user,
--   u.created_at
-- FROM users u
-- JOIN roles r ON u.id_role = r.id_role
-- ORDER BY u.id_user;

-- Resultado esperado:
-- id_user | username_user        | email_user                   | name_role     | status_user | created_at
-- 1       | admin.sistema        | admin@untels.edu.pe          | ADMINISTRADOR | true        | 2026-05-11...
-- 2       | juan.bibliotecario   | juan.gonzales@untels.edu.pe  | BIBLIOTECARIO | true        | 2026-05-11...
-- 3       | maria.bibliotecaria  | maria.lopez@untels.edu.pe    | BIBLIOTECARIO | true        | 2026-05-11...
-- 4       | cesar.salazar        | 2213110208@untels.edu.pe     | ESTUDIANTE    | true        | 2026-05-11...
-- 5       | christopher.risco    | 2213010394@untels.edu.pe     | ESTUDIANTE    | true        | 2026-05-11...
-- 6       | curo.valenzuela      | curo.valenzuela@untels.edu.pe| ESTUDIANTE    | true        | 2026-05-11...

-- ========================================================================================================
-- 9. INFORMACIÓN DEL SCRIPT
-- ========================================================================================================
-- Tablas creadas: 3 (roles, users, token_blacklist)
-- Índices creados: 4 (para performance)
-- Roles insertados: 3 (ADMINISTRADOR, BIBLIOTECARIO, ESTUDIANTE)
-- Usuarios insertados: 6 (1 Admin, 2 Bibliotecarios, 3 Estudiantes)
--
-- ADMINISTRADOR: ÚNICO en el sistema (id_user=1)
-- BIBLIOTECARIOS: 2 usuarios (id_user=2,3)
-- ESTUDIANTES: 3 usuarios (id_user=4,5,6)
--
-- Token_blacklist: Vacía al inicio, se llena cuando users hacen LOGOUT
-- ========================================================================================================

-- ========================================================================================================
-- 10. MENSAJE FINAL
-- ========================================================================================================
-- Si ves este mensaje, el script se ejecutó correctamente ✓
-- ========================================================================================================

