-- =====================================================================
-- datos_prueba.sql - Sistema Integral de Gestion Bibliotecaria (UNTELS)
--
-- IMPORTANTE: este script SOLO inserta datos. NO crea ni borra tablas.
-- Las tablas ya las crea Spring Boot automaticamente (Hibernate,
-- spring.jpa.hibernate.ddl-auto=update) la primera vez que arrancas
-- el backend contra la base de datos "bdbiblioteca".
--
-- El modelo REAL del proyecto tiene solo 6 tablas (verificado contra
-- el backend real, no existen tablas separadas de Autor, Categoria,
-- Editorial, Devolucion, Rol, Bibliotecario o Estudiante: todo eso
-- vive dentro de "usuarios" y "libros" como columnas simples):
--   usuarios, libros, prestamos, sanciones, notificaciones,
--   configuracion_biblioteca
--
-- Datos incluidos:
--   2 administradores, 5 bibliotecarios, 5 estudiantes (12 usuarios)
--   30 libros
--   20 prestamos (8 de ellos ya "devueltos" = las devoluciones)
--   10 sanciones
--   20 notificaciones
--   1 fila de configuracion
--
-- Contrasena de TODAS las cuentas de prueba: Password123!
-- (ya viene como hash BCrypt real, no la escribas en texto plano)
--
-- Uso: abrir esta base en pgAdmin -> bdbiblioteca -> Query Tool -> pegar
-- todo el contenido -> Execute (F5). Ver el paso a paso en prueba.md.
-- =====================================================================

-- Vacía las tablas para poder ejecutar este script varias veces sin
-- duplicar datos ni chocar con los UNIQUE de username/codigo/isbn.
-- RESTART IDENTITY reinicia los contadores para que los IDs queden
-- siempre 1..12 en usuarios, 1..30 en libros, etc. (los mismos que usa
-- esta guía para las pruebas).
TRUNCATE TABLE notificaciones, sanciones, prestamos, libros, usuarios, configuracion_biblioteca
    RESTART IDENTITY CASCADE;

-- =====================================================================
-- USUARIOS: 2 admin (id 1-2) + 5 bibliotecarios (id 3-7) + 5 estudiantes (id 8-12)
-- Cada hash BCrypt corresponde exactamente al proporcionado.
-- =====================================================================
INSERT INTO usuarios (username, password, codigo, carnet, dni, nombre, email, telefono, rol, carrera, ciclo, estado) VALUES
('admin1',     '$2a$12$7/8spXDjcdh3xUJxlxZSGuJoa/Zge2lKKuTr7vwWLfYmjfir0lpkq', 'ADM-0001', NULL,     '10000001', 'Admin General',   'admin1@untels.edu.pe',     '999000001', 'ADMIN',         NULL,                     NULL, 'ACTIVO'),   -- 1
('admin2',     '$2a$12$N5TS/vYxKvAo2Lptwsqr7uZ7Yh7MSaTUmcMlC/PgE0p6nU0FiYHIK', 'ADM-0002', NULL,     '10000002', 'Admin Secundario','admin2@untels.edu.pe',     '999000002', 'ADMIN',         NULL,                     NULL, 'ACTIVO'),   -- 2
('biblio1',    '$2a$12$/eOx79LC6F./Aje8cMhvheQ8vJjMW0zzKHgC.SImqa4zxh1j4KFya', 'BIB-0001', NULL,     '10000003', 'Rosa Mendoza',    'rmendoza@untels.edu.pe',   '999000003', 'BIBLIOTECARIO', NULL,                     NULL, 'ACTIVO'),   -- 3
('biblio2',    '$2a$12$6KTwcqvZJyWUzyvzukP05eA7ER2VTcCj2pw9dOiYktz/2Ikcp0rf6', 'BIB-0002', NULL,     '10000004', 'Jorge Salinas',   'jsalinas@untels.edu.pe',   '999000004', 'BIBLIOTECARIO', NULL,                     NULL, 'ACTIVO'),   -- 4
('biblio3',    '$2a$12$HUg9YA1UNfMQz3Gqea7beuf2vlWT1nnAzwSH5vePrGsM5v/6L.GHC', 'BIB-0003', NULL,     '10000005', 'Carmen Diaz',     'cdiaz@untels.edu.pe',      '999000005', 'BIBLIOTECARIO', NULL,                     NULL, 'ACTIVO'),   -- 5
('biblio4',    '$2a$12$9dmF1R8pbEKWjTQzQFCzv.0iiJIqd1.9GSEUjzyTBYg1IVtraYovS', 'BIB-0004', NULL,     '10000006', 'Pedro Vargas',    'pvargas@untels.edu.pe',    '999000006', 'BIBLIOTECARIO', NULL,                     NULL, 'ACTIVO'),   -- 6
('biblio5',    '$2a$12$3KMT/.pg//tWuWIkNQP/p.yHQvDGEo/oXz0ecI2yzogfRm96ufTE.', 'BIB-0005', NULL,     '10000007', 'Lucia Rojas',     'lrojas@untels.edu.pe',     '999000007', 'BIBLIOTECARIO', NULL,                     NULL, 'ACTIVO'),   -- 7
('est1',       '$2a$12$xg3ti4wnuJJOzYE.FBIKq.XnbZdjv2Go3NgBe0infIh0JZFeuVD7y', 'EST-1001', 'C-1001', '20000001', 'Juan Perez',      'est1@untels.edu.pe',       '988000001', 'ESTUDIANTE',    'Ingenieria de Sistemas', 5,    'ACTIVO'),   -- 8
('est2',       '$2a$12$Fw2BU.LbNftVkVWZiCASuuuF2FkSp5ou.qDcL4BuEscUWB9So76D2', 'EST-1002', 'C-1002', '20000002', 'Maria Lopez',     'est2@untels.edu.pe',       '988000002', 'ESTUDIANTE',    'Ingenieria Industrial',  3,    'ACTIVO'),   -- 9
('est3',       '$2a$12$0qXUfFfdaYr5rwEF1nahvuuNpvl7XkliApzkg.vF4pCR5kK2wJIJ2', 'EST-1003', 'C-1003', '20000003', 'Carlos Ramirez',  'est3@untels.edu.pe',       '988000003', 'ESTUDIANTE',    'Administracion',         7,    'ACTIVO'),   -- 10 (tiene sancion activa)
('est4',       '$2a$12$1rmPJ9/9ZR2Mc6D44aj39.1aup3lLpGV7Py7itZydJOYzKp2Bti/q', 'EST-1004', 'C-1004', '20000004', 'Ana Torres',      'est4@untels.edu.pe',       '988000004', 'ESTUDIANTE',    'Contabilidad',           2,    'INACTIVO'), -- 11 (para probar login bloqueado)
('est5',       '$2a$12$uOWtnUzTaR.d1T.ximul8.MQUoyNGMYLfHsoKgP3EzqSkOQqb8VR2', 'EST-1005', 'C-1005', '20000005', 'Luis Fernandez',  'est5@untels.edu.pe',       '988000005', 'ESTUDIANTE',    'Ingenieria de Sistemas', 9,    'ACTIVO');  -- 12

-- =====================================================================
-- LIBROS: 30 libros (id 1..30). "autor", "categoria" y "editorial" son
-- columnas de texto simples dentro de la tabla libros (no hay tablas
-- separadas de Autor/Categoria/Editorial en este proyecto).
-- =====================================================================
INSERT INTO libros (titulo, autor, isbn, editorial, anio, categoria, stock, stock_total, descripcion, recurso) VALUES
('Redes de Computadoras',                'Andrew Tanenbaum',        '9786073209001', 'Pearson',       2019, 'TECNICO',    5, 5,  'Fundamentos de redes de computadoras.', NULL),               -- 1
('Bases de Datos Avanzadas',              'Raghu Ramakrishnan',      '9789701068784', 'McGraw-Hill',   2018, 'TECNICO',    1, 3,  'Modelado y administracion de BD.', NULL),                     -- 2
('Ingenieria de Software',                'Ian Sommerville',         '9788478290812', 'Pearson',       2020, 'TECNICO',    4, 6,  'Ciclo de vida del software.', NULL),                          -- 3
('Estructuras de Datos y Algoritmos',     'Robert Lafore',           '9789702612493', 'Pearson',       2017, 'TECNICO',    3, 4,  'Estructuras de datos en Java.', NULL),                        -- 4
('Sistemas Operativos Modernos',          'Andrew Tanenbaum',        '9786073214554', 'Pearson',       2021, 'TECNICO',    2, 4,  'Conceptos de sistemas operativos.', NULL),                    -- 5
('Programacion en Java',                  'Herbert Schildt',         '9786071512260', 'McGraw-Hill',   2019, 'TECNICO',    6, 6,  'Guia completa de Java.', NULL),                               -- 6
('Redes Neuronales y Deep Learning',      'Ian Goodfellow',          '9788428338414', 'Anaya',         2022, 'TECNICO',    2, 3,  'Introduccion al deep learning.', NULL),                       -- 7
('Arquitectura de Computadoras',          'David Patterson',         '9788429130791', 'Reverte',       2018, 'TECNICO',    3, 5,  'Diseno y organizacion de computadoras.', NULL),               -- 8
('Seguridad Informatica',                 'William Stallings',       '9788490354271', 'Pearson',       2020, 'TECNICO',    1, 2,  'Fundamentos de seguridad y criptografia.', NULL),             -- 9
('Ingenieria Web',                        'Luis Joyanes Aguilar',    '9786071509894', 'McGraw-Hill',   2016, 'TECNICO',    0, 2,  'Desarrollo de aplicaciones web.', NULL),                      -- 10
('Contabilidad General',                  'Zeida Garcia',            '9789972615401', 'San Marcos',    2020, 'REFERENCIA', 2, 4,  'Principios basicos de contabilidad.', NULL),                  -- 11
('Introduccion a la Administracion',      'Idalberto Chiavenato',    '9786071511478', 'McGraw-Hill',   2017, 'REFERENCIA', 0, 2,  'Conceptos fundamentales de administracion.', NULL),          -- 12
('Marketing Estrategico',                 'Philip Kotler',           '9788490356336', 'Pearson',       2019, 'REFERENCIA', 3, 4,  'Fundamentos de marketing.', NULL),                            -- 13
('Finanzas Corporativas',                 'Stephen Ross',            '9786071511768', 'McGraw-Hill',   2018, 'REFERENCIA', 2, 3,  'Analisis financiero corporativo.', NULL),                     -- 14
('Derecho Empresarial',                   'Julio Cesar Rivera',      '9789972455401', 'San Marcos',    2015, 'REFERENCIA', 4, 5,  'Fundamentos del derecho para empresas.', NULL),               -- 15
('Estadistica Aplicada',                  'Mario Triola',            '9786073212567', 'Pearson',       2018, 'REFERENCIA', 3, 4,  'Estadistica para ciencias sociales.', NULL),                  -- 16
('Recursos Humanos',                      'Idalberto Chiavenato',    '9786071512895', 'McGraw-Hill',   2019, 'REFERENCIA', 2, 3,  'Gestion del talento humano.', NULL),                          -- 17
('Economia Politica',                     'Paul Samuelson',          '9786071511119', 'McGraw-Hill',   2014, 'REFERENCIA', 1, 2,  'Introduccion a la economia.', NULL),                          -- 18
('Auditoria y Control Interno',           'Rodrigo Estupinan',       '9789587710356', 'ECOE',          2017, 'REFERENCIA', 2, 2,  'Auditoria financiera y control interno.', NULL),              -- 19
('Cien Anios de Soledad',                 'Gabriel Garcia Marquez',  '9788439732472', 'Sudamericana',  1967, 'FICCION',    4, 4,  'Novela emblematica del realismo magico.', NULL),              -- 20
('Don Quijote de la Mancha',              'Miguel de Cervantes',     '9788420412147', 'Alfaguara',     1605, 'FICCION',    3, 3,  'Clasico de la literatura espanola.', NULL),                   -- 21
('1984',                                  'George Orwell',           '9788499890944', 'Debolsillo',    1949, 'FICCION',    5, 5,  'Novela distopica.', NULL),                                    -- 22
('La Ciudad y los Perros',                'Mario Vargas Llosa',      '9788420471694', 'Alfaguara',     1963, 'FICCION',    2, 3,  'Novela peruana contemporanea.', NULL),                        -- 23
('El Principito',                        'Antoine de Saint-Exupery','9788498381498', 'Salamandra',    1943, 'FICCION',    6, 6,  'Fabula filosofica ilustrada.', NULL),                         -- 24
('Rayuela',                               'Julio Cortazar',          '9788439733042', 'Alfaguara',     1963, 'FICCION',    1, 2,  'Novela experimental argentina.', NULL),                       -- 25
('Cronica de una Muerte Anunciada',       'Gabriel Garcia Marquez',  '9788497592386', 'Debolsillo',    1981, 'FICCION',    3, 3,  'Novela corta de suspenso.', NULL),                            -- 26
('Los Rios Profundos',                    'Jose Maria Arguedas',     '9789972313456', 'Peisa',         1958, 'FICCION',    2, 2,  'Novela indigenista peruana.', NULL),                          -- 27
('Fahrenheit 451',                        'Ray Bradbury',            '9788445077825', 'Debolsillo',    1953, 'FICCION',    2, 4,  'Novela de ciencia ficcion distopica.', NULL),                 -- 28
('Crimen y Castigo',                      'Fiodor Dostoievski',      '9788420674971', 'Alianza',       1866, 'FICCION',    1, 2,  'Novela psicologica rusa.', NULL);                             -- 29 (29 filas; se agrega la 30 abajo)

INSERT INTO libros (titulo, autor, isbn, editorial, anio, categoria, stock, stock_total, descripcion, recurso) VALUES
('La Odisea',                             'Homero',                  '9788424936830', 'Catedra',       -700, 'FICCION',   2, 2,  'Poema epico griego clasico.', NULL); -- 30

-- =====================================================================
-- PRESTAMOS: 20 registros (id 1..20), referenciando usuarios (8,9,10,12)
-- y libros (1..30). 8 de ellos ya estan en estado 'devuelto' (equivalen
-- a las "devoluciones").
-- =====================================================================
INSERT INTO prestamos (id_libro, id_estudiante, fecha, fecha_recojo, fecha_entrega, fecha_confirmacion, fecha_devolucion, estado, motivo, curso, observaciones, estado_devolucion, observaciones_dev) VALUES
(1,  8,  NOW() - INTERVAL '40 days', NOW() - INTERVAL '39 days', NOW() - INTERVAL '26 days', NULL, NOW() - INTERVAL '27 days', 'devuelto', 'Trabajo de investigacion de redes', 'Redes I',        'Ninguna', 'BUENO',   'Buen estado'),          -- 1
(2,  9,  NOW() - INTERVAL '38 days', NOW() - INTERVAL '37 days', NOW() - INTERVAL '24 days', NULL, NOW() - INTERVAL '25 days', 'devuelto', 'Proyecto de curso de BD',           'Base de Datos',  'Ninguna', 'BUENO',   'Sin novedad'),          -- 2
(3,  10, NOW() - INTERVAL '35 days', NOW() - INTERVAL '34 days', NOW() - INTERVAL '21 days', NULL, NOW() - INTERVAL '20 days', 'devuelto', 'Consulta de ingenieria de software','Ing. Software',  'Ninguna', 'DAÑADO',  'Hojas dobladas'),        -- 3
(4,  12, NOW() - INTERVAL '33 days', NOW() - INTERVAL '32 days', NOW() - INTERVAL '19 days', NULL, NOW() - INTERVAL '18 days', 'devuelto', 'Estudio de algoritmos',             'Algoritmos',     'Ninguna', 'BUENO',   'Buen estado'),          -- 4
(6,  8,  NOW() - INTERVAL '30 days', NOW() - INTERVAL '29 days', NOW() - INTERVAL '16 days', NULL, NOW() - INTERVAL '15 days', 'devuelto', 'Practicas de programacion',         'Programacion I', 'Ninguna', 'BUENO',   'Buen estado'),          -- 5
(8,  9,  NOW() - INTERVAL '28 days', NOW() - INTERVAL '27 days', NOW() - INTERVAL '14 days', NULL, NOW() - INTERVAL '13 days', 'devuelto', 'Arquitectura de sistemas',          'Arquitectura',   'Ninguna', 'BUENO',   'Buen estado'),          -- 6
(11, 10, NOW() - INTERVAL '25 days', NOW() - INTERVAL '24 days', NOW() - INTERVAL '11 days', NULL, NOW() - INTERVAL '10 days', 'devuelto', 'Trabajo de contabilidad',           'Contabilidad',   'Ninguna', 'BUENO',   'Buen estado'),          -- 7
(20, 12, NOW() - INTERVAL '22 days', NOW() - INTERVAL '21 days', NOW() - INTERVAL '8 days',  NULL, NOW() - INTERVAL '9 days',  'devuelto', 'Lectura recreativa',                'Literatura',     'Ninguna', 'BUENO',   'Buen estado'),          -- 8
(2,  9,  NOW() - INTERVAL '10 days', NOW() - INTERVAL '9 days',  NOW() + INTERVAL '5 days',  NULL, NULL,                       'vigente',  'Proyecto de curso de BD',           'Base de Datos',  'Ninguna', NULL,      NULL),                   -- 9
(5,  8,  NOW() - INTERVAL '9 days',  NOW() - INTERVAL '8 days',  NOW() + INTERVAL '6 days',  NULL, NULL,                       'vigente',  'Estudio de sistemas operativos',    'Sist. Operativos','Ninguna', NULL,     NULL),                   -- 10
(9,  10, NOW() - INTERVAL '7 days',  NOW() - INTERVAL '6 days',  NOW() + INTERVAL '8 days',  NULL, NULL,                       'vigente',  'Investigacion de seguridad',        'Seguridad',      'Ninguna', NULL,      NULL),                   -- 11
(24, 12, NOW() - INTERVAL '6 days',  NOW() - INTERVAL '5 days',  NOW() + INTERVAL '9 days',  NULL, NULL,                       'vigente',  'Lectura de curso de literatura',     'Literatura',     'Ninguna', NULL,      NULL),                  -- 12
(3,  10, NOW() - INTERVAL '20 days', NOW() - INTERVAL '19 days', NOW() - INTERVAL '5 days',  NULL, NULL,                       'vencido',  'Consulta de ingenieria',            'Ing. Software',  'Ninguna', NULL,      NULL),                   -- 13
(13, 9,  NOW() - INTERVAL '18 days', NOW() - INTERVAL '17 days', NOW() - INTERVAL '3 days',  NULL, NULL,                       'vencido',  'Trabajo de marketing',              'Marketing',      'Ninguna', NULL,      NULL),                   -- 14
(7,  8,  NOW() - INTERVAL '1 days',  NULL,                       NOW() + INTERVAL '14 days', NULL, NULL,                       'solicitado','Lectura de redes neuronales',      'IA',             'Ninguna', NULL,      NULL),                   -- 15
(16, 9,  NOW() - INTERVAL '1 days',  NULL,                       NOW() + INTERVAL '14 days', NULL, NULL,                       'solicitado','Trabajo de estadistica',            'Estadistica',    'Ninguna', NULL,      NULL),                   -- 16
(22, 12, NOW() - INTERVAL '2 days',  NULL,                       NOW() + INTERVAL '13 days', NULL, NULL,                       'solicitado','Lectura recomendada del curso',     'Literatura',     'Ninguna', NULL,      NULL),                   -- 17
(28, 10, NOW() - INTERVAL '2 days',  NULL,                       NOW() + INTERVAL '13 days', NULL, NULL,                       'solicitado','Lectura de ciencia ficcion',        'Literatura',     'Ninguna', NULL,      NULL),                   -- 18
(10, 8,  NOW() - INTERVAL '4 days',  NULL,                       NOW() + INTERVAL '11 days', NULL, NULL,                       'rechazado','Consulta rapida',                   'Ing. Web',       'Ninguna', NULL,      NULL),                   -- 19
(12, 9,  NOW() - INTERVAL '3 days',  NULL,                       NOW() + INTERVAL '12 days', NULL, NULL,                       'rechazado','Consulta rapida',                   'Administracion', 'Ninguna', NULL,      NULL);                   -- 20

-- =====================================================================
-- SANCIONES: 10 registros (3 activas, 7 cumplidas)
-- =====================================================================
INSERT INTO sanciones (id_estudiante, motivo, dias_suspension, multa, estado, fecha_creacion, fecha_fin) VALUES
(10, 'Devolucion tardia de libro: Ingenieria de Software (5 dias de retraso)', 5, 5.00, 'activa',   NOW() - INTERVAL '5 days',  NOW() + INTERVAL '2 days'),
(9,  'Devolucion tardia de libro: Marketing Estrategico (3 dias de retraso)',  3, 3.00, 'activa',   NOW() - INTERVAL '3 days',  NOW() + INTERVAL '4 days'),
(8,  'Devolucion tardia de libro: Estructuras de Datos (2 dias de retraso)',   2, 2.00, 'activa',   NOW() - INTERVAL '2 days',  NOW() + INTERVAL '5 days'),
(12, 'Devolucion tardia de libro: Redes de Computadoras (2 dias de retraso)',  2, 2.00, 'cumplida', NOW() - INTERVAL '60 days', NOW() - INTERVAL '58 days'),
(8,  'Devolucion tardia de libro: Bases de Datos Avanzadas (1 dia de retraso)',1, 1.00, 'cumplida', NOW() - INTERVAL '55 days', NOW() - INTERVAL '54 days'),
(9,  'Devolucion tardia de libro: Contabilidad General (4 dias de retraso)',   4, 4.00, 'cumplida', NOW() - INTERVAL '50 days', NOW() - INTERVAL '46 days'),
(10, 'Devolucion tardia de libro: Programacion en Java (3 dias de retraso)',   3, 3.00, 'cumplida', NOW() - INTERVAL '45 days', NOW() - INTERVAL '42 days'),
(12, 'Devolucion tardia de libro: Cien Anios de Soledad (2 dias de retraso)',  2, 2.00, 'cumplida', NOW() - INTERVAL '40 days', NOW() - INTERVAL '38 days'),
(8,  'Devolucion tardia de libro: Sistemas Operativos Modernos (6 dias de retraso)', 6, 6.00, 'cumplida', NOW() - INTERVAL '35 days', NOW() - INTERVAL '29 days'),
(9,  'Devolucion tardia de libro: Arquitectura de Computadoras (1 dia de retraso)', 1, 1.00, 'cumplida', NOW() - INTERVAL '30 days', NOW() - INTERVAL '29 days');

-- =====================================================================
-- NOTIFICACIONES: 20 registros
-- =====================================================================
INSERT INTO notificaciones (id_estudiante, tipo, mensaje, fecha, leida) VALUES
(8,  'confirmacion', 'Tu prestamo del libro ''Redes de Computadoras'' ha sido aprobado', NOW() - INTERVAL '39 days', TRUE),
(9,  'confirmacion', 'Tu prestamo del libro ''Bases de Datos Avanzadas'' ha sido aprobado', NOW() - INTERVAL '37 days', TRUE),
(10, 'confirmacion', 'Tu prestamo del libro ''Ingenieria de Software'' ha sido aprobado', NOW() - INTERVAL '34 days', TRUE),
(12, 'confirmacion', 'Tu prestamo del libro ''Estructuras de Datos y Algoritmos'' ha sido aprobado', NOW() - INTERVAL '32 days', TRUE),
(8,  'confirmacion', 'Tu prestamo del libro ''Programacion en Java'' ha sido aprobado', NOW() - INTERVAL '29 days', TRUE),
(9,  'confirmacion', 'Tu prestamo del libro ''Arquitectura de Computadoras'' ha sido aprobado', NOW() - INTERVAL '27 days', TRUE),
(10, 'confirmacion', 'Tu prestamo del libro ''Contabilidad General'' ha sido aprobado', NOW() - INTERVAL '24 days', TRUE),
(12, 'confirmacion', 'Tu prestamo del libro ''Cien Anios de Soledad'' ha sido aprobado', NOW() - INTERVAL '21 days', TRUE),
(9,  'confirmacion', 'Tu prestamo del libro ''Bases de Datos Avanzadas'' ha sido aprobado', NOW() - INTERVAL '9 days', FALSE),
(8,  'confirmacion', 'Tu prestamo del libro ''Sistemas Operativos Modernos'' ha sido aprobado', NOW() - INTERVAL '8 days', FALSE),
(10, 'confirmacion', 'Tu prestamo del libro ''Seguridad Informatica'' ha sido aprobado', NOW() - INTERVAL '6 days', FALSE),
(12, 'confirmacion', 'Tu prestamo del libro ''El Principito'' ha sido aprobado', NOW() - INTERVAL '5 days', FALSE),
(10, 'sancion', 'Se ha generado una sancion por devolucion tardia. Dias de suspension: 5, Multa: S/5.00', NOW() - INTERVAL '5 days', FALSE),
(9,  'sancion', 'Se ha generado una sancion por devolucion tardia. Dias de suspension: 3, Multa: S/3.00', NOW() - INTERVAL '3 days', FALSE),
(8,  'sancion', 'Se ha generado una sancion por devolucion tardia. Dias de suspension: 2, Multa: S/2.00', NOW() - INTERVAL '2 days', FALSE),
(8,  'rechazo', 'Tu solicitud de prestamo del libro ''Ingenieria Web'' ha sido rechazada: No hay ejemplares disponibles', NOW() - INTERVAL '4 days', FALSE),
(9,  'rechazo', 'Tu solicitud de prestamo del libro ''Introduccion a la Administracion'' ha sido rechazada: No hay ejemplares disponibles', NOW() - INTERVAL '3 days', FALSE),
(8,  'recordatorio', 'Tu prestamo esta proximo a vencer, recuerda devolverlo a tiempo', NOW() - INTERVAL '1 days', FALSE),
(9,  'recordatorio', 'Tu prestamo esta proximo a vencer, recuerda devolverlo a tiempo', NOW() - INTERVAL '1 days', FALSE),
(10, 'recordatorio', 'Tu prestamo esta vencido, por favor devuelvelo lo antes posible', NOW(), FALSE);

-- =====================================================================
-- CONFIGURACION (fila unica)
-- =====================================================================
INSERT INTO configuracion_biblioteca (dias_max_prestamo, limite_prestamos, multa_por_dia, scheduler_activo, notif_email, alerta_stock, modo_mant) VALUES
(15, 3, 2.00, TRUE, FALSE, TRUE, FALSE);

-- =====================================================================
-- VERIFICACION RAPIDA (puedes ejecutar esto por separado despues)
-- =====================================================================
-- SELECT 'usuarios' AS tabla, count(*) FROM usuarios
-- UNION ALL SELECT 'libros', count(*) FROM libros
-- UNION ALL SELECT 'prestamos', count(*) FROM prestamos
-- UNION ALL SELECT 'sanciones', count(*) FROM sanciones
-- UNION ALL SELECT 'notificaciones', count(*) FROM notificaciones
-- UNION ALL SELECT 'configuracion_biblioteca', count(*) FROM configuracion_biblioteca;
