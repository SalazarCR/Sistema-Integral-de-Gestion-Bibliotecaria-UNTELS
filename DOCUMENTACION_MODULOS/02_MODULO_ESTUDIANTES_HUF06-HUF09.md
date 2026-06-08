# DOCUMENTACIÓN COMPLETA DEL MÓDULO DE GESTIÓN DE ESTUDIANTES (HUF06-HUF09)

## Sistema Integral de Gestión de Biblioteca Académica Universitaria (SIGBAU)

**Fecha de Generación:** 2026-06-01  
**Desarrollador Principal:** Carlos Curo (Curo)  
**Commits Asignados:** 4  
**SLOC Asignado:** ~140 líneas  
**Período:** 2026-04-27 a 2026-05-11 (participación parcial)

---

## 1. CASO PRÁCTICO DEL MÓDULO

### 1.1 Identificación del Módulo

| Aspecto | Descripción |
|--------|-------------|
| **Nombre** | Módulo de Gestión de Estudiantes y Carreras |
| **Código** | HUF06-HUF09 |
| **Objetivo** | Registrar estudiantes, asignar carreras, controlar acceso biblioteca, gestión CRUD completo |
| **Dependencia** | HUF01-HUF05 (User, Role entities) |
| **Usuarios Finales** | Administradores, Bibliotecarios, Estudiantes |
| **Stack Tecnológico** | Java 17, Spring Boot, JPA/Hibernate, PostgreSQL |

### 1.2 Descripción Funcional

**HUF06:** Registrar Estudiantes
- Endpoint POST /api/students/register
- Crear entidad Student linked a User
- Validar email único
- Asignar carrera

**HUF07:** Actualizar Información Estudiante
- PATCH /api/students/{id}
- Editar datos: nombre, apellido, email, carrera
- Auditoría de cambios

**HUF08:** Gestión de Carreras
- CRUD completo para Carrera
- Relación OneToMany con Student
- Consultar estudiantes por carrera

**HUF09:** Control de Acceso Biblioteca
- Campo libraryAccessStudent (boolean)
- Estudiantes pueden acceder solo si = true
- Endpoint PATCH para toggle acceso

### 1.3 Datos de Implementación Real

**Commits Realizados:** 4 commits  
**Participación:** Parcial (trabajo en paralelo con Cesar)  
**Líneas de Código:** ~140 SLOC  
**Entidades Principales:** Student, Carrera

---

## 2. WBS – ESTRUCTURA DE DESGLOSE DEL TRABAJO

### 2.1 Desglose Jerárquico

```
HUF06-HUF09: GESTIÓN DE ESTUDIANTES Y CARRERAS
│
├─ HUF06: Registrar Estudiantes
│  ├─ Entidad Student
│  ├─ DTO StudentRegisterDTO
│  ├─ Repositorio IStudentRepository
│  ├─ Servicio StudentServiceImplement
│  └─ Controller StudentController (POST)
│
├─ HUF07: Actualizar Estudiante
│  ├─ DTO StudentUpdateDTO
│  ├─ Servicio updateStudent()
│  ├─ Controller (PATCH /api/students/{id})
│  └─ Validaciones de cambio
│
├─ HUF08: Gestión Carreras
│  ├─ Entidad Carrera
│  ├─ DTO CarreraDTO
│  ├─ Repositorio ICarreraRepository
│  ├─ Servicio CarreraServiceImplement (CRUD)
│  ├─ Controller CarreraController
│  └─ Endpoints: GET, POST, PUT, DELETE
│
└─ HUF09: Control Acceso Biblioteca
   ├─ Campo libraryAccessStudent en Student
   ├─ Endpoint PATCH toggle acceso
   ├─ Validación en endpoints de biblioteca
   └─ Log de cambios acceso
```

### 2.2 Tabla WBS Detallada

| Nivel | Componente | Duración (días) | Dependencias |
|-------|-----------|-----------------|-------------|
| Diseño | Mapeo Student-Carrera-User | 1 | HUF01-HUF05 ✅ |
| Entidades | Student + Carrera | 1.5 | Diseño |
| DTOs | StudentRegisterDTO, StudentUpdateDTO | 1 | Entidades |
| Repositorios | IStudentRepository, ICarreraRepository | 0.5 | Entidades |
| Servicios | StudentService, CarreraService | 2 | Repositorios |
| Controladores | StudentController, CarreraController | 1.5 | Servicios |
| Validaciones | Constraints @Valid, @NotNull | 1 | DTOs |
| Testing | Unit tests para servicios | 1 | Servicios |
| **Total** | | **9.5 días** | |

---

## 3. ESTIMACIÓN POR PUNTOS DE FUNCIÓN

### 3.1 Análisis de Funciones

#### EI (External Inputs)

| Función | Entrada | Complejidad | PF | Cantidad | Total |
|---------|---------|-------------|-------|----------|--------|
| registerStudent() | 6 campos (nombre, apellido, email, idCarrera, etc) | Media | 4 | 1 | **4** |
| updateStudent() | 4 campos | Media | 4 | 1 | **4** |
| createCarrera() | 2 campos (name, status) | Baja | 3 | 1 | **3** |
| toggleLibraryAccess() | 1 campo (boolean) | Baja | 3 | 1 | **3** |
| **SUBTOTAL EI** | | | | | **14** |

#### EO (External Outputs)

| Función | Salida | Complejidad | PF | Cantidad | Total |
|---------|--------|-------------|-------|----------|--------|
| StudentResponseDTO | 7 campos (id, nombre, email, carrera, status, etc) | Media | 5 | 1 | **5** |
| CarreraResponseDTO | 4 campos | Baja | 4 | 1 | **4** |
| **SUBTOTAL EO** | | | | | **9** |

#### EQ (External Queries)

| Función | Búsqueda | Complejidad | PF | Cantidad | Total |
|---------|---------|-------------|-------|----------|--------|
| findStudentByEmail() | 1 campo | Baja | 3 | 1 | **3** |
| findStudentsByCarrera() | 1 campo | Baja | 3 | 1 | **3** |
| getStudentsByStatus() | 1 campo | Baja | 3 | 1 | **3** |
| **SUBTOTAL EQ** | | | | | **9** |

#### ILF (Internal Logical Files)

| Archivo | Registros | Complejidad | PF | Cantidad | Total |
|---------|-----------|-------------|-------|----------|--------|
| students | ~2000 registros | Media | 10 | 1 | **10** |
| carreras | ~20 registros | Baja | 7 | 1 | **7** |
| **SUBTOTAL ILF** | | | | | **17** |

#### EIF (External Interface Files)

| Interfaz | Descripción | Complejidad | PF | Cantidad | Total |
|----------|------------|-------------|-------|----------|--------|
| users (Link Student-User) | Integración con módulo Auth | Media | 7 | 1 | **7** |
| **SUBTOTAL EIF** | | | | | **7** |

### 3.2 Resumen PF

| Tipo | Subtotal | % |
|------|----------|-------|
| EI | 14 | 22% |
| EO | 9 | 14% |
| EQ | 9 | 14% |
| ILF | 17 | 27% |
| EIF | 7 | 11% |
| EIF no implementados | -7 | -11% |
| **Total PF Desarrollado** | **56** | **100%** |

---

## 4. CONVERSIÓN A SLOC

**Factor SLOC/PF:** 55 (Spring Boot)

```
SLOC = 56 PF × 55 = 3,080 SLOC
KLOC = 3.08 KLOC

SLOC Real Actual: 140 líneas
Porcentaje Realizado: (140 / 3,080) × 100 = 4.5% (aún en desarrollo)
```

---

## 5. ESTIMACIÓN COCOMO II

```
E_base = 2.94 × (3.08)^1.10
E_base = 2.94 × 3.43 = 10.08 PM

EM = 1.10 (Cloud infrastructure)
E_ajustado = 10.08 × 1.10 = 11.1 PM

TDEV = 3.67 × (11.1)^0.28 = 3.67 × 1.98 = 7.3 meses

Equipo óptimo = 11.1 / 7.3 = 1.5 personas
```

| Métrica | Valor |
|---------|-------|
| Esfuerzo Base | 10 PM |
| Esfuerzo Ajustado | 11 PM |
| Duración | 7.3 meses |
| Equipo Óptimo | 1.5 personas |

---

## 6. PLAN DE RECURSOS Y PRESUPUESTO

### 6.1 Recursos Asignados

| Perfil | Cantidad | Duración | Costo Mensual | Subtotal |
|--------|----------|----------|---------------|----------|
| Desarrollador (Curo) | 1 (part-time) | 7.3 meses | $1,800 | $13,140 |
| QA/Testing | 0.5 | 3.7 meses | $1,500 | $2,775 |
| **Total Módulo** | | | | **$15,915** |

---

### 6.2 Presupuesto Detallado

| Concepto | Monto USD |
|----------|-----------|
| Recursos Humanos | $15,915 |
| Infraestructura (prorrateado) | $1,000 |
| Licencias (prorrateado) | $400 |
| Subtotal | $17,315 |
| Contingencia (5%) | $866 |
| **Presupuesto Total Módulo** | **$18,181** |

---

## 7. CRONOGRAMA

**Duración Planeada:** 7.3 meses  
**Inicio Real:** 2026-04-27  
**Fin Estimado:** 2026-12-15  

| Sprint | Período | Actividad | Estado |
|--------|---------|----------|--------|
| 1 | Mayo 1-15 | Diseño + Entidades | ✅ 50% |
| 2 | Mayo 16-30 | DTOs + Repos + Servicios | ⏳ 30% |
| 3 | Junio 1-15 | Controllers + Validaciones | ⏳ 0% |
| 4 | Junio 16-30 | Testing + QA | ⏳ 0% |
| 5+ | Julio+ | Refinamiento + Integración | ⏳ 0% |

---

## 8. MATRIZ DE RIESGOS

| ID | Riesgo | Prob | Impacto | Nivel | Mitigación |
|----|--------|------|---------|-------|-----------|
| R-201 | Conflictos User-Student relationship | 25% | Medio | BAJO | Validar JPA foreign keys |
| R-202 | Email duplicado entre Students | 30% | Medio | BAJO | @Unique constraint |
| R-203 | Carrera sin estudiantes - deletion issues | 20% | Alto | MEDIO | ON DELETE RESTRICT |
| R-204 | libraryAccess toggle sin auditoría | 40% | Medio | MEDIO | Log + Timestamp |
| R-205 | Sprint delay por conflictos merge | 35% | Alto | MEDIO | Branch strategy clara |

---

## 9. KPIs

| Métrica | Valor | Target | Status |
|---------|-------|--------|--------|
| Commits | 4 | 8-10 | ⚠️ 50% |
| Code Coverage | 10% | 75% | 🔴 |
| Bug Density | 2/KLOC | <5 | ✅ |
| Lead Time | 15 días | <10 | ⚠️ |
| Sprint Velocity | 4 commits | 8 | ⚠️ |

---

## 10. EVM

**BAC:** $18,181  
**Período Completado:** 15 días (2.1% de 7.3 meses)

```
PV = $18,181 × 0.021 = $382
EV = $18,181 × 0.30 = $5,454 (30% completado)
AC = $1,800 × (15/30) = $900

SPI = $5,454 / $382 = 14.29 (Adelanto)
CPI = $5,454 / $900 = 6.06 (Bajo costo)
```

**Status:** 🟡 AMARILLO (Parcial, necesita aceleración)

---

## 11. RECOMENDACIONES

✅ **Logros:**
- Entidades diseñadas correctamente
- Relaciones con User validadas
- DTOs iniciales creados

⚠️ **Riesgos:**
- Baja velocidad (4 commits en 15 días)
- 0% test coverage
- Participación part-time de Curo

🔧 **Acciones:**
1. Acelerar implementación de servicios
2. Agregar testing en Sprint 2
3. Validar constraints de DB
4. Revisar scope HUF06-HUF09

---

**Documento Generado:** 2026-06-01  
**Estado:** ✅ VERSIÓN 1.0  
**Próxima Revisión:** 2026-06-15

