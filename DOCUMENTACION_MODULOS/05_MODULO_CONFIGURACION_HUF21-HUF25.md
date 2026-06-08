# DOCUMENTACIÓN COMPLETA DEL MÓDULO DE CONFIGURACIÓN DE PARÁMETROS (HUF21-HUF25)

## Sistema Integral de Gestión de Biblioteca Académica Universitaria (SIGBAU)

**Fecha de Generación:** 2026-06-01  
**Desarrollador Principal:** Nipper (Ecker Ruiz García)  
**Commits Asignados:** 2  
**SLOC Asignado:** ~120 líneas  
**Período:** 2026-04-27 a 2026-05-11

---

## 1. CASO PRÁCTICO DEL MÓDULO

| Aspecto | Descripción |
|--------|-------------|
| **Nombre** | Módulo de Configuración de Parámetros |
| **Código** | HUF21-HUF25 |
| **Objetivo** | CRUD de parámetros de configuración, valores por tipo (stock mínimo, días préstamo, límite préstamos) |
| **Usuarios** | Solo Administrador |
| **Stack** | Spring Boot, PostgreSQL |

### Funcionalidades

**HUF17-HUF19:** (Precursoras - Stock mínimo, días préstamo, límite préstamos)
- ConfigParametro con tipos: STOCK_MINIMO, DIAS_PRESTAMO, LIMITE_PRESTAMOS

**HUF21:** Registrar Parámetro
- POST /api/config/parameters
- Solo ADMIN
- Validar tipo y valor

**HUF22:** Consultar Parámetro
- GET /api/config/parameters/{tipo}
- Cache de 1 hora

**HUF23:** Actualizar Parámetro
- PUT /api/config/parameters/{id}
- Solo ADMIN

**HUF24-HUF25:** Validación y Auditoría
- Cambios auditados
- Historial de valores

---

## 2. WBS

```
HUF21-HUF25: CONFIGURACIÓN PARÁMETROS
├─ HUF21: Registrar Parámetro
│  ├─ Entidad ConfigParametro
│  ├─ Enum TipoParametro (STOCK, DIAS, LIMITE)
│  ├─ DTO ConfigParametroDTO
│  └─ Endpoint POST
├─ HUF22: Consultar (con cache)
│  ├─ Repository findByTipo()
│  ├─ @Cacheable Spring
│  └─ Endpoint GET
├─ HUF23: Actualizar
│  ├─ Validations
│  ├─ Audit logging
│  └─ Endpoint PUT
└─ HUF24-HUF25: Auditoría
   ├─ Tabla config_parameter_audit
   ├─ Logger cambios
   └─ Trace de cambios
```

---

## 3. ESTIMACIÓN POR PUNTOS DE FUNCIÓN

| Tipo | Función | Complejidad | PF | Total |
|------|---------|-------------|-------|--------|
| EI | registerParameter() | Media | 4 | 4 |
| EI | updateParameter() | Media | 4 | 4 |
| EI | validateParameter() | Baja | 3 | 3 |
| EO | ParametroResponseDTO | Baja | 4 | 4 |
| EQ | findByTipo() | Baja | 3 | 3 |
| EQ | getHistory() | Baja | 3 | 3 |
| ILF | config_parametros | Baja (20 registros) | 7 | 7 |
| ILF | config_audit | Baja | 7 | 7 |
| **Total PF** | | | | **35 PF** |

```
SLOC = 35 × 55 = 1,925 líneas
KLOC = 1.925

E = 2.94 × (1.925)^1.10 = 6.5 PM
TDEV = 3.67 × (6.5)^0.28 = 5.7 meses
Equipo: 1.1 personas
```

---

## 4. PRESUPUESTO

| Concepto | Monto |
|----------|-------|
| Recursos (1.1 dev × 5.7 meses) | $9,975 |
| Infraestructura | $800 |
| Licenses | $300 |
| **Subtotal** | $11,075 |
| Contingencia (5%) | $554 |
| **Total** | $11,629 |

---

## 5. CRONOGRAMA (Paralelo a otros módulos)

| Sprint | Período | Objetivo |
|--------|---------|----------|
| 1-2 | Mayo | Entidades + REST API |
| 3 | Junio | Caching + Auditoría |
| 4+ | Julio | Integration testing |

---

## 6. MATRIZ DE RIESGOS

| Riesgo | Prob | Impacto | Mitigación |
|--------|------|---------|-----------|
| Caché invalidation | 25% | Medio | Event-driven invalidation |
| Valores inválidos usuario | 30% | Bajo | @Min @Max validation |
| Acceso no admin | 15% | Alto | @PreAuthorize("ROLE_ADMIN") |

---

## 7. EVM

```
BAC = $11,629
PV (15 días) = $374
EV (2 commits/35 PF = 5%) = $581
AC = $700

SPI = 1.55 (Adelanto)
CPI = 0.83 (Sobre costo)
Status: 🟡 BAJO PROGRESO
```

---

**Documento Generado:** 2026-06-01  
**Estado:** ✅ VERSIÓN 1.0

