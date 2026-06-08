-- ========================================================================================================
-- SCRIPT SQL: TABLA LIBROS
-- Proyecto: APIBiblioteca
-- Fecha: 08/06/2026
-- Descripción: Crea tabla de libros para el módulo de gestión del catálogo
-- ========================================================================================================

-- ========================================================================================================
-- 1. CREAR TABLA: LIBROS
-- ========================================================================================================
CREATE TABLE IF NOT EXISTS libros (
    id_libro SERIAL PRIMARY KEY,
    isbn VARCHAR(13) UNIQUE NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(200) NOT NULL,
    editorial VARCHAR(200),
    categoria VARCHAR(100),
    ano_publicacion INTEGER,
    descripcion TEXT,
    url_portada VARCHAR(500),
    stock INTEGER NOT NULL DEFAULT 0,
    stock_total INTEGER NOT NULL DEFAULT 0,
    paginas INTEGER,
    idioma VARCHAR(50),
    status_libro BOOLEAN NOT NULL DEFAULT true,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    creado_por VARCHAR(100),
    actualizado_por VARCHAR(100)
);

-- ========================================================================================================
-- 2. CREAR ÍNDICES PARA PERFORMANCE
-- ========================================================================================================
CREATE INDEX IF NOT EXISTS idx_libro_isbn ON libros(isbn);
CREATE INDEX IF NOT EXISTS idx_libro_titulo ON libros(titulo);
CREATE INDEX IF NOT EXISTS idx_libro_categoria ON libros(categoria);
CREATE INDEX IF NOT EXISTS idx_libro_status ON libros(status_libro);
CREATE INDEX IF NOT EXISTS idx_libro_stock ON libros(stock);

-- ========================================================================================================
-- 3. COMENTARIOS SOBRE LA TABLA
-- ========================================================================================================
-- isbn: Identificador único internacional de libros (10 o 13 caracteres)
-- titulo: Título del libro (obligatorio)
-- autor: Autor del libro (obligatorio)
-- editorial: Casa editorial que publicó el libro
-- categoria: Categoría o género del libro (ej: Ficción, Ciencia, Matemáticas)
-- ano_publicacion: Año de publicación del libro
-- descripcion: Descripción completa o sinopsis del libro
-- url_portada: URL de la portada obtenida de OpenLibrary
-- stock: Cantidad disponible actualmente
-- stock_total: Cantidad total en el catálogo
-- paginas: Número de páginas del libro
-- idioma: Idioma principal del libro
-- status_libro: Estado del libro (true = activo, false = eliminado/inactivo)
-- fecha_creacion: Fecha de registro en el sistema
-- fecha_actualizacion: Fecha de última modificación
-- creado_por: Usuario que registró el libro
-- actualizado_por: Usuario que realizó la última actualización

-- ========================================================================================================
-- 4. DATOS DE EJEMPLO (OPCIONAL)
-- ========================================================================================================
INSERT INTO libros (isbn, titulo, autor, editorial, categoria, ano_publicacion, descripcion,
                     stock, stock_total, paginas, idioma, status_libro, creado_por, actualizado_por)
VALUES
(
    '978-8441532015',
    'Don Quijote',
    'Miguel de Cervantes',
    'Editorial Planeta',
    'Clásicos de la Literatura',
    1605,
    'La historia del ingeniero hidalgo que cree ser un caballero andante',
    5,
    10,
    1200,
    'Español',
    true,
    'ADMIN',
    'ADMIN'
)
ON CONFLICT (isbn) DO NOTHING;

-- ========================================================================================================
-- 5. VERIFICACIÓN
-- ========================================================================================================
-- SELECT COUNT(*) as total_libros FROM libros WHERE status_libro = true;
-- SELECT * FROM libros LIMIT 10;

-- ========================================================================================================
-- NOTA IMPORTANTE
-- ========================================================================================================
-- Esta tabla está configurada para integrarse con:
-- 1. Entidad JPA: Libro.java
-- 2. Repositorio: ILibroRepository.java
-- 3. Servicio: ILibroService.java y LibroServiceImplement.java
-- 4. Controlador: LibroController.java
-- 5. DTOs: LibroDTO.java, LibroResponseDTO.java, LibroApiExternaDTO.java
--
-- La tabla soporta:
-- - Búsquedas por ISBN exacto
-- - Búsquedas por título (parcial)
-- - Búsquedas por categoría
-- - Búsquedas por autor (parcial)
-- - Filtrados por estado y disponibilidad
-- - Consultas a OpenLibrary API para enriquecer datos

