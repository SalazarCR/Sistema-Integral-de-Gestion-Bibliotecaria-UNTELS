# DOCUMENTACIÓN COMPLETA DEL MÓDULO DE CATÁLOGO DE LIBROS (HUF15-HUF20)

## Sistema Integral de Gestión de Biblioteca Académica Universitaria (SIGBAU)

**Fecha de Generación:** 2026-06-01  
**Desarrollador Principal:** Nick Ronald  
**Commits Asignados:** 2  
**SLOC Asignado:** ~150 líneas  
**Período:** 2026-04-27 a 2026-05-11 (participación inicial)

---

## 1. CASO PRÁCTICO DEL MÓDULO

| Aspecto | Descripción |
|--------|-------------|
| **Nombre** | Módulo de Gestión de Catálogo de Libros |
| **Código** | HUF15-HUF20 |
| **Objetivo** | CRUD de libros, búsqueda, integración APIs externas (Open Library, Google Books, Covers.io) |
| **Stack** | Spring Boot, RestTemplate, PostgreSQL |
| **APIs Externas** | Open Library, Google Books, Librivox, Covers.io |

### Funcionalidades

**HUF15:** Registrar Libros
- POST /api/books
- Validar ISBN único
- Integración Open Library API

**HUF16:** Buscar Libros
- GET /api/books/search?query=
- Búsqueda por título, autor, ISBN
- Filtrar por categoría

**HUF17:** Ver Detalles del Libro
- GET /api/books/{id}
- Obtener portada desde Covers.io
- Datos de Open Library

**HUF18-HUF20:** Gestión Stock
- Crear relación Book-Stock
- Registrar cantidad disponible
- Control de préstamos

---

## 2. WBS

```
HUF15-HUF20: CATÁLOGO DE LIBROS
├─ HUF15: Registrar Libros
│  ├─ Entidades: Book, Author, Category
│  ├─ DTOs: BookRegisterDTO, BookResponseDTO
│  ├─ API Integration (Open Library)
│  └─ Endpoint POST /api/books
├─ HUF16: Búsqueda Avanzada
│  ├─ Especificaciones @Query
│  ├─ Índices fulltext search
│  └─ Endpoint GET /api/books/search
├─ HUF17: Detalles Libro + Portada
│  ├─ Covers.io Integration
│  ├─ Cache de imágenes
│  └─ Endpoint GET /api/books/{id}
└─ HUF18-HUF20: Gestión Stock
   ├─ Entidad BookStock
   ├─ Relación Book-Stock
   ├─ Control de disponibilidad
   └─ Endpoints CRUD Stock
```

---

## 3. ESTIMACIÓN POR PUNTOS DE FUNCIÓN

| Tipo | Función | Complejidad | PF | Cantidad | Total |
|------|---------|-------------|-------|----------|--------|
| EI | registerBook() | Alta (Open Library) | 6 | 1 | 6 |
| EI | registerStock() | Media | 4 | 1 | 4 |
| EO | BookDetailDTO | Alta (con portada) | 7 | 1 | 7 |
| EQ | searchBooks() | Alta (fulltext) | 6 | 1 | 6 |
| EQ | findByAuthor() | Media | 4 | 1 | 4 |
| ILF | books table | Alta (20+ campos) | 15 | 1 | 15 |
| ILF | book_stock table | Media | 10 | 1 | 10 |
| ILF | authors table | Baja | 7 | 1 | 7 |
| ILF | categories table | Baja | 7 | 1 | 7 |
| EIF | Open Library API | Alta | 10 | 1 | 10 |
| EIF | Covers.io API | Media | 7 | 1 | 7 |
| **Total PF** | | | | | **84 PF** |

```
SLOC = 84 × 55 = 4,620 líneas
KLOC = 4.62

E = 2.94 × (4.62)^1.10 = 14.2 PM
TDEV = 3.67 × (14.2)^0.28 = 7.8 meses
Equipo: 1.8 personas

Recomendado: 2 developers (1 back, 1 front-end)
```

---

## 4. PRESUPUESTO

| Concepto | Monto |
|----------|-------|
| Dev Back-End | $13,500 (1.8 dev × 7.8 meses) |
| Dev Front-End (UI) | $10,800 (1.5 dev × 7.8 meses) |
| QA/Testing | $5,850 (1 dev × 0.5 × 7.8 meses) |
| API Credits (Open Library) | $500 |
| Cloud Infrastructure | $1,500 |
| **Subtotal** | $32,150 |
| Contingencia (5%) | $1,608 |
| **Total Módulo** | $33,758 |

---

## 5. COCOMO II

**Esfuerzo:** 14.2 PM  
**Duración:** 7.8 meses  
**Equipo:** 1.8 personas

**Observación:** Módulo más complejo por integraciones externas

---

## 6. CRONOGRAMA

| Sprint | Período | Objetivo | Hitos |
|--------|---------|----------|-------|
| 1-2 | Mayo 1-30 | Design + API integration | Open Library API functional |
| 3 | Junio 1-15 | Registrar libros + búsqueda | 100 libros en BD |
| 4 | Junio 16-30 | Stock management | Integración con préstamos |
| 5+ | Julio+ | Covers.io + Librivox | Portadas + audiobooks |

---

## 7. MATRIZ DE RIESGOS

| Riesgo | Prob | Impacto | Mitigación |
|--------|------|---------|-----------|
| API Timeout (Open Library) | 40% | Alto | Circuit breaker + caching |
| ISBN duplicado | 25% | Medio | @Unique + validación |
| Performance búsqueda | 35% | Alto | Índices fulltext + pagination |
| Rate limit APIs | 30% | Medio | Queue + retry strategy |
| Image loading Covers.io | 20% | Bajo | CDN + fallback |

---

## 8. KPIs

| Métrica | Valor | Target |
|---------|-------|--------|
| Commits | 2 | 5-8 |
| API Integration | 2 APIs | 4 APIs |
| Search Performance | < 500ms target | <300ms |
| Data Quality | 95% | 98% |

---

## 9. EVM

```
BAC = $33,758
PV (15 días) = $33,758 × 0.032 = $1,080
EV (2 commits/84 PF = 5%) = $33,758 × 0.05 = $1,688
AC = $1,200

SPI = 1.56 (Adelanto)
CPI = 1.41 (Bajo costo) 
Status: 🟡 LENTO - necesita aceleración
```

---

## 10. RECOMENDACIONES

⚠️ **CRÍTICO:**
1. Documentar APIs externas (versiones, endpoints)
2. Implementar circuit breaker para Open Library
3. Crear cache strategy para portadas
4. Establecer tests de integración con APIs

🔧 **Acciones Inmediatas:**
- Acelerar desarrollo (solo 2 commits en 15 días)
- Agregar dev front-end para UI de búsqueda
- Validar SLA de APIs externas

---

**Documento Generado:** 2026-06-01  
**Estado:** ✅ VERSIÓN 1.0  
**Prioridad:** 🔴 ALTA (módulo crítico)

