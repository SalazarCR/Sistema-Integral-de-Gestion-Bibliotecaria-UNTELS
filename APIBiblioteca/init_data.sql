-- Script SQL para insertar datos iniciales en la BD
-- Ejecutar en pgAdmin en la base de datos: bdbiblioteca

-- Insertar Roles
INSERT INTO "Role" (id_role, name_role) VALUES
(1, 'Admin'),
(2, 'Bibliotecario'),
(3, 'Estudiante')
ON CONFLICT DO NOTHING;

-- Insertar Usuarios Admin (contraseña: admin123 encriptada con BCrypt)
-- Para cambiar contraseña: usar un encodificador online o bcrypt en Java
INSERT INTO "User" (id_user, username_user, password_user, email_user, status_user, date_register_user, id_role) VALUES
(1, 'admin', '$2a$10$JwKk.LuZnZfBj4kVyS8x9OlxlN6p7Klr2kKlQpQvQkQlKbVxK9FVK', 'admin@untels.edu.pe', true, NOW(), 1)
ON CONFLICT DO NOTHING;

-- Insertar Usuario Bibliotecario
INSERT INTO "User" (id_user, username_user, password_user, email_user, status_user, date_register_user, id_role) VALUES
(2, 'bibliotecario', '$2a$10$JwKk.LuZnZfBj4kVyS8x9OlxlN6p7Klr2kKlQpQvQkQlKbVxK9FVK', 'biblio@untels.edu.pe', true, NOW(), 2)
ON CONFLICT DO NOTHING;

-- Insertar Carreras
INSERT INTO "Carrera" (id_carrera, name_carrera) VALUES
(1, 'Ingeniería de Sistemas'),
(2, 'Ingeniería Civil'),
(3, 'Administración de Empresas'),
(4, 'Contabilidad')
ON CONFLICT DO NOTHING;

-- Insertar Estudiantes Ejemplo (usuario_id 3, 4, 5)
INSERT INTO "User" (id_user, username_user, password_user, email_user, status_user, date_register_user, id_role) VALUES
(3, 'estudiante1', '$2a$10$JwKk.LuZnZfBj4kVyS8x9OlxlN6p7Klr2kKlQpQvQkQlKbVxK9FVK', 'estudiante1@untels.edu.pe', true, NOW(), 3),
(4, 'estudiante2', '$2a$10$JwKk.LuZnZfBj4kVyS8x9OlxlN6p7Klr2kKlQpQvQkQlKbVxK9FVK', 'estudiante2@untels.edu.pe', true, NOW(), 3),
(5, 'estudiante3', '$2a$10$JwKk.LuZnZfBj4kVyS8x9OlxlN6p7Klr2kKlQpQvQkQlKbVxK9FVK', 'estudiante3@untels.edu.pe', false, NOW(), 3)
ON CONFLICT DO NOTHING;

-- Insertar Estudiantes en la tabla Student
INSERT INTO "Student" (id_student, codigo_student, name_student, email_student, phone_student, status_student, library_access_student, date_register_student, id_carrera, id_user) VALUES
(1, 'EST2024001', 'Juan Perez Garcia', 'juan.perez@untels.edu.pe', '987654321', true, true, NOW()::date, 1, 3),
(2, 'EST2024002', 'Maria Martinez Lopez', 'maria.martinez@untels.edu.pe', '987654322', true, true, NOW()::date, 2, 4),
(3, 'EST2024003', 'Carlos Rodriguez Flores', 'carlos.rodriguez@untels.edu.pe', '987654323', false, false, NOW()::date, 1, 5)
ON CONFLICT DO NOTHING;

-- Verificar que los datos fueron insertados
SELECT 'ROLES INSERTADOS:' as Mensaje;
SELECT * FROM "Role" LIMIT 5;

SELECT 'USUARIOS INSERTADOS:' as Mensaje;
SELECT id_user, username_user, email_user, status_user FROM "User" LIMIT 10;

SELECT 'ESTUDIANTES INSERTADOS:' as Mensaje;
SELECT id_student, codigo_student, name_student, status_student, library_access_student FROM "Student" LIMIT 5;

