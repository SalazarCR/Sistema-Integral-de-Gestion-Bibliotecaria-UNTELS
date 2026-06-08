# DOCUMENTACIÓN COMPLETA DEL MÓDULO DE GESTIÓN DE BIBLIOTECARIOS (HUF10-HUF14)

## Sistema Integral de Gestión de Biblioteca Académica Universitaria (SIGBAU)

**Fecha de Generación:** 2026-06-01  
**Desarrollador Principal:** Christopher Risco  
**Commits Asignados:** 5  
**SLOC Asignado:** ~200 líneas  
**Período:** 2026-04-27 a 2026-05-11

---

## 1. CASO PRÁCTICO DEL MÓDULO

| Aspecto | Descripción |
|--------|-------------|
| **Nombre** | Módulo de Gestión de Bibliotecarios |
| **Código** | HUF10-HUF14 |
| **Objetivo** | CRUD completo de bibliotecarios, asignación de permisos, gestión de acceso |
| **Dependencia** | HUF01-HUF05 (User, Role entities) |
| **Stack** | Spring Boot, JPA, PostgreSQL |

### Funcionalidades

**HUF10:** Registrar Bibliotecarios
- POST /api/librarians/register
- Validar username único
- Asignar rol BIBLIOTECARIO

**HUF11:** Visualizar Bibliotecarios
- GET /api/librarians
- Filtrar por estado

**HUF12:** Editar Bibliotecarios
- PUT /api/librarians/{id}
- Actualizar datos

**HUF13:** Deshabilitar Bibliotecarios
- DELETE /api/librarians/{id}
- Soft delete (cambiar status)

**HUF14:** Toggle Estado Acceso
- PATCH /api/librarians/{id}/toggle-status
- Activar/desactivar bibliotecario

---

## 2. WBS

```
HUF10-HUF14: GESTIÓN BIBLIOTECARIOS
├─ HUF10: Registrar
│  ├─ Entidad Librarian (extends User)
│  ├─ DTO LibrarianRegisterDTO
│  ├─ Servicio register()
│  └─ Endpoint POST
├─ HUF11: Visualizar
│  ├─ findAll(), findByStatus()
│  └─ Endpoint GET
├─ HUF12: Editar
│  ├─ updateLibrarian()
│  └─ Endpoint PUT
├─ HUF13: Deshabilitar
│  ├─ softDelete()
│  └─ Endpoint DELETE
└─ HUF14: Toggle Status
   └─ Endpoint PATCH
```

---

## 3. ESTIMACIÓN POR PUNTOS DE FUNCIÓN

| Tipo | Función | Complejidad | PF | Cantidad | Total |
|------|---------|-------------|-------|----------|--------|
| EI | registerLibrarian() | Media | 4 | 1 | 4 |
| EI | updateLibrarian() | Media | 4 | 1 | 4 |
| EI | deleteLibrarian() | Baja | 3 | 1 | 3 |
| EO | LibrarianResponseDTO | Media | 5 | 1 | 5 |
| EQ | findLibrariansByStatus() | Baja | 3 | 1 | 3 |
| ILF | librarians table | Media | 10 | 1 | 10 |
| **Total PF** | | | | | **32 PF** |

```
SLOC = 32 × 55 = 1,760 líneas
KLOC = 1.76

E = 2.94 × (1.76)^1.10 = 5.9 PM
TDEV = 3.67 × (5.9)^0.28 = 5.5 meses
Equipo: 1.1 personas
```

---

## 4. PRESUPUESTO

| Concepto | Monto |
|----------|-------|
| Recursos Humanos | $11,000 (1 dev × 5.5 meses) |
| Infraestructura | $600 |
| Licencias | $250 |
| **Subtotal** | $11,850 |
| Contingencia (5%) | $593 |
| **Total** | $12,443 |

---

## 5. CRONOGRAMA

| Sprint | Período | Objetivo | Estado |
|--------|---------|----------|--------|
| 1-2 | Mayo 1-30 | Entidades + Servicios | 🔄 40% |
| 3 | Junio 1-15 | Controllers + Testing | ⏳ 0% |
| 4+ | Junio 16+ | Integration | ⏳ 0% |

---

## 6. MATRIZ DE RIESGOS

| Riesgo | Prob | Impacto | Mitigación |
|--------|------|---------|-----------|
| Conflictos con módulo Admin | 30% | Medio | Definir límites de permisos |
| Soft delete inconsistencia | 25% | Medio | Validar status en queries |
| Duplicado de email | 20% | Bajo | @Unique constraint |

---

## 7. EVM

```
BAC = $12,443
PV (15 días) = $12,443 × 0.029 = $361
EV (5 commits/32 PF = 15%) = $12,443 × 0.15 = $1,867
AC = $1,200 (1 dev × 15 días)

SPI = 5.17 (Adelanto significativo)
CPI = 1.56 (Bajo costo unitario)
```

---

## 8. RECOMENDACIONES

✅ **Logros:**
- 5 commits en periodo
- Ritmo consistente con otros devs

⚠️ **Acciones Críticas:**
1. Implementar tests unitarios URGENTE
2. Definir relación Librarian-User (inheritance vs composition)
3. Validar constraints de permisos

---

**Documento Generado:** 2026-06-01  
**Estado:** ✅ VERSIÓN 1.0

