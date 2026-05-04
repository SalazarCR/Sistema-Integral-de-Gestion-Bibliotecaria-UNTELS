-- Script SQL para insertar datos iniciales en la BD
-- Ejecutar en pgAdmin en la base de datos: bdbiblioteca

-- 🔑 1. INSERTAR ROLES
INSERT INTO role (id_role, name_role, description_role) VALUES
(1, 'Admin', NULL),
(2, 'Bibliotecario', NULL),
(3, 'Estudiante', NULL);

-- 🏫 2. INSERTAR CARRERAS
INSERT INTO carrera (id_carrera, status_carrera, name_carrera, description_carrera) VALUES
(2, true, 'Ingeniería de Sistemas', NULL),
(3, true, 'Ingeniería Civil', NULL),
(4, true, 'Administración', NULL);

-- 👤 3. INSERTAR USUARIOS (LOGIN)
INSERT INTO "user" (
   id_user,
   id_role,
   status_user,
   date_register_user,
   username_user,
   email_user,
   password_user
) VALUES
(1, 2, true, '2026-05-03 21:00:00', 'bibliotecario1', 'bibliotecario1@untels.edu.pe', '1234'),
(2, 3, true, '2026-05-03 21:00:00', 'juan.perez', 'juan.perez@untels.edu.pe', '1234'),
(3, 3, true, '2026-05-03 21:00:00', 'maria.lopez', 'maria.lopez@untels.edu.pe', '1234'),
(4, 3, true, '2026-05-03 21:00:00', 'carlos.garcia', 'carlos.garcia@untels.edu.pe', '1234'),
(5, 1, true, '2026-05-04 00:09:32', 'admin', 'admin@untels.edu.pe', '1234');

-- 🎓 4. INSERTAR ESTUDIANTES
INSERT INTO student (
   id_student,
   id_user,
   id_carrera,
   date_register_student,
   library_access_student,
   status_student,
   codigo_student,
   phone_student,
   email_student,
   name_student
) VALUES
(3, 2, 2, '2026-05-03', true, true, 'EST2024001', '987654321', 'juan.perez@untels.edu.pe', 'Juan Perez Gonzales'),
(4, 3, 3, '2026-05-03', true, true, 'EST2024002', '987654322', 'maria.lopez@untels.edu.pe', 'Maria Lopez Martinez'),
(5, 4, 2, '2026-05-03', false, false, 'EST2024003', '987654323', 'carlos.garcia@untels.edu.pe', 'Carlos Garcia Rodriguez');

-- ✅ VERIFICAR QUE LOS DATOS FUERON INSERTADOS
SELECT 'ROLES INSERTADOS:' as Mensaje;
SELECT * FROM role;

SELECT 'CARRERAS INSERTADAS:' as Mensaje;
SELECT * FROM carrera;

SELECT 'USUARIOS INSERTADOS:' as Mensaje;
SELECT id_user, username_user, email_user, status_user, id_role FROM "user";

SELECT 'ESTUDIANTES INSERTADOS:' as Mensaje;
SELECT id_student, codigo_student, name_student, status_student, library_access_student FROM student;

