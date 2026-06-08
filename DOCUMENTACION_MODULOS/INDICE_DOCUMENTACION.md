# 📚 ÍNDICE DE DOCUMENTACIÓN - PROYECTO SIGBAU

## Estructura de Documentos Generados

Documentación Completa del Proyecto SIGBAU - Sistema Integral de Gestión de Biblioteca Académica Universitaria (UNTELS)

**Período de Generación:** 2026-04-27 a 2026-06-01  
**Total de Documentos:** 7  
**Total de Páginas:** ~100  
**Formato:** Markdown (compatible GitHub)

---

## 🎯 DOCUMENTOS POR NIVEL

### NIVEL 1: EJECUTIVO (Para Directivos)

📄 **00_RESUMEN_EJECUTIVO_CONSOLIDADO.md** ⭐ INICIAR AQUÍ
- Status general proyecto
- Presupuesto consolidado: $251,123 USD
- Timeline: 9 meses (2026-04-27 a 2026-09-15)
- Top 5 riesgos críticos
- Recomendaciones estratégicas
- EVM análisis consolidado
- Dashboard visual
- **Tiempo de lectura:** 20-30 minutos
- **Audiencia:** PM, Directivos, Stakeholders, Cliente

---

### NIVEL 2: MÓDULOS (Para Gerentes de Proyecto)

📄 **01_MODULO_AUTENTICACION_HUF01-HUF05.md**
- **Desarrollador:** Cesar Salazar
- **Commits:** 32 | SLOC: 280 | Presupuesto: $104,422
- **Contenido Detallado:**
  - WBS con 8 niveles
  - 104 Puntos de Función (27% del proyecto)
  - COCOMO II: 22 PM esfuerzo, 9 meses duración
  - Matriz riesgos 8 riesgos identificados
  - KPIs: Velocity 128% ✅, Coverage 0% 🔴
  - EVM: SPI 14.39, CPI 30.48
  - Cronograma detallado
- **Archivos relacionados:**
  - GUIA_COMPLETA_ROLE_TOKEN_CONFIG_PARAMETRO.md
  - CODIGO_COMENTADO_ROLE_TOKEN_USER.md
  - bdbiblioteca_sprint2_init.sql
- **Tiempo de lectura:** 45-60 minutos

📄 **02_MODULO_ESTUDIANTES_HUF06-HUF09.md**
- **Desarrollador:** Carlos Curo
- **Commits:** 4 | SLOC: 140 | Presupuesto: $18,181
- **Puntos de Función:** 56 PF (14% del proyecto)
- **COCOMO II:** 11 PM, 7.3 meses
- **Status:** 30% completado
- **Tiempo de lectura:** 20-30 minutos

📄 **03_MODULO_BIBLIOTECARIOS_HUF10-HUF14.md**
- **Desarrollador:** Christopher Risco
- **Commits:** 5 | SLOC: 200 | Presupuesto: $12,443
- **Puntos de Función:** 32 PF (8% del proyecto)
- **COCOMO II:** 6 PM, 5.5 meses
- **Status:** 40% completado
- **Tiempo de lectura:** 20 minutos

📄 **04_MODULO_LIBROS_HUF15-HUF20.md** 🔴 CRÍTICO
- **Desarrollador:** Nick Ronald
- **Commits:** 2 | SLOC: 150 | Presupuesto: $33,758
- **Puntos de Función:** 84 PF (21% del proyecto)
- **APIs Externas:** Open Library, Google Books, Covers.io, Librivox
- **COCOMO II:** 14.2 PM, 7.8 meses
- **Status:** 5% completado (LENTO - ALERTA)
- **Riesgos:** Timeout APIs, rate limiting, performance búsqueda
- **Tiempo de lectura:** 25 minutos

📄 **05_MODULO_CONFIGURACION_HUF21-HUF25.md**
- **Desarrollador:** Nipper (Ecker Ruiz García)
- **Commits:** 2 | SLOC: 120 | Presupuesto: $11,629
- **Puntos de Función:** 35 PF (9% del proyecto)
- **COCOMO II:** 6.5 PM, 5.7 meses
- **Status:** 5% completado
- **Tiempo de lectura:** 15 minutos

📄 **06_MODULO_QA_TESTING_HUF26-HUF30.md** 🔴 CRÍTICO
- **Desarrollador:** Jair1711 (Jair Flores)
- **Commits:** 4 | Testing strategy | Presupuesto: $15,771
- **Puntos de Función:** 74 PF (~19% proyecto)
- **COCOMO II:** 9.8 PM, 6.9 meses
- **Estrategia:** Pirámide Testing (Unit 60%, Integration 30%, E2E 10%)
- **Target Coverage:** 75%+ (ACTUAL: 0% - CRÍTICA)
- **Status:** Preparation phase (10%)
- **Tiempo de lectura:** 25 minutos

---

## 🗂️ ESTRUCTURA FÍSICA DEL DIRECTORIO

```
SIGBAU_PROYECTO/
│
├─ DOCUMENTACION_MODULOS/
│  ├─ 00_RESUMEN_EJECUTIVO_CONSOLIDADO.md ⭐
│  ├─ 01_MODULO_AUTENTICACION_HUF01-HUF05.md
│  ├─ 02_MODULO_ESTUDIANTES_HUF06-HUF09.md
│  ├─ 03_MODULO_BIBLIOTECARIOS_HUF10-HUF14.md
│  ├─ 04_MODULO_LIBROS_HUF15-HUF20.md (CRÍTICO)
│  ├─ 05_MODULO_CONFIGURACION_HUF21-HUF25.md
│  ├─ 06_MODULO_QA_TESTING_HUF26-HUF30.md (CRÍTICO)
│  └─ INDICE_DOCUMENTACION.md (este archivo)
│
├─ APIBiblioteca/
│  ├─ src/main/java/.../
│  ├─ src/test/... (VACÍO - TODO en Sprint 2)
│  ├─ GUIA_COMPLETA_ROLE_TOKEN_CONFIG_PARAMETRO.md
│  ├─ CODIGO_COMENTADO_ROLE_TOKEN_USER.md
│  ├─ bdbiblioteca_sprint2_init.sql
│  └─ build.gradle
│
└─ .git/ (51 commits, 1,399 SLOC actual)
```

---

## 📊 MATRIZ DE REFERENCIAS CRUZADAS

### Por Métrica de Proyecto

#### **Presupuesto por Módulo**
| Módulo | Monto | % | Documento |
|--------|-------|-------|---------|
| Autenticación | $104,422 | **41.6%** | 01 |
| Libros | $33,758 | **13.4%** | 04 |
| Estudiantes | $18,181 | **7.2%** | 02 |
| QA/Testing | $15,771 | **6.3%** | 06 |
| Bibliotecarios | $12,443 | **5.0%** | 03 |
| Configuración | $11,629 | **4.6%** | 05 |
| **Overhead (PM/DevOps)** | **$55,000** | **21.9%** | 00 |

#### **Puntos de Función por Tipo**
| Tipo | PF | % | Documentado |
|------|----|----|----------|
| EI (Inputs) | 69 | 18% | Todos módulos |
| EO (Outputs) | 58 | 15% | Todos módulos |
| EQ (Queries) | 41 | 11% | Todos módulos |
| ILF (Archivos) | 107 | 27% | Todos módulos |
| EIF (Interfaces) | 41 | 11% | Todos módulos |
| Testing support | 72 | 18% | Documento 06 |

#### **Commits por Desarrollador**
| Desarrollador | Commits | % | Módulo | Documento |
|---------------|---------|------|--------|-----------|
| Cesar Salazar | 32 | 62.7% | Autenticación | 01 |
| Carlos Curo | 4 | 7.8% | Estudiantes | 02 |
| Christopher Risco | 5 | 9.8% | Bibliotecarios | 03 |
| Nick Ronald | 2 | 3.9% | Libros | 04 |
| Nipper (Ecker) | 2 | 3.9% | Configuración | 05 |
| Jair1711 | 4 | 7.8% | QA/Testing | 06 |
| **TOTAL** | **51** | **100%** | - | 00 |

---

## 🚀 CÓMO UTILIZAR ESTA DOCUMENTACIÓN

### Para Directivos / Stakeholders
1. Leer **00_RESUMEN_EJECUTIVO_CONSOLIDADO.md** (30 min)
2. Focus en secciones:
   - 1. Vista General del Proyecto
   - 5. Presupuesto Total
   - 10. Recomendaciones Estratégicas
   - 13. Conclusión y Recomendación Final
3. Preguntas clave respondidas:
   - ¿Cuál es el status del proyecto?
   - ¿Cuánto va a costar?
   - ¿Cuándo estará listo?
   - ¿Qué riesgos debería preocuparme?

### Para Gerentes de Módulo
1. Leer documento del módulo específico (25-45 min)
2. Focus en secciones:
   - 1. Caso Práctico
   - 3. WBS
   - 5. COCOMO II
   - 7-9. Riesgos, KPIs, EVM
3. Luego leer **00_RESUMEN_EJECUTIVO** para contexto global
4. Referencia: EVM para control semanal

### Para Desarrolladores
1. Leer documento de su módulo (15-30 min)
2. Focus en secciones:
   - 2. WBS (entiender que hacer)
   - 3. Puntos de Función (scope)
   - 6. Recursos asignados (su equipo)
   - 8. Matriz de Riesgos (mitigar)
3. Revisar archivos técnicos relacionados:
   - GUIA_COMPLETA_ROLE_TOKEN_CONFIG_PARAMETRO.md
   - CODIGO_COMENTADO_ROLE_TOKEN_USER.md
   - Código fuente en APIBiblioteca/

### Para QA / Testing
1. Leer **06_MODULO_QA_TESTING_HUF26-HUF30.md** (25 min)
2. Focus en secciones:
   - 6. Estrategia de Testing
   - 8. Herramientas y Frameworks
   - 11. Hitos Críticos
3. Revisar **00_RESUMEN** sección 10 (KPIs + Métricas)
4. Crear test plans basados en:
   - Matriz de riesgos
   - Criterios de aceptación (sprints)
   - Checklist pre-Go-Live

### Para DevOps / Infrastructure
1. Leer secciones de infraestructura en **00_RESUMEN**
2. Focus en:
   - Presupuesto AWS/Cloud
   - CI/CD gates
   - Configuración application.properties
   - bdbiblioteca_sprint2_init.sql

---

## 🔍 BÚSQUEDA RÁPIDA POR TEMA

### Autenticación & Seguridad
- **Documento:** 01_MODULO_AUTENTICACION_HUF01-HUF05.md
- **Secciones:**
  - JWT Tokens & TokenBlacklist (Sección 1.2)
  - Spring Security Config (Sección 2)
  - RBAC - Role Based Access Control (Sección 3)
  - Riesgos de Seguridad (Sección 8)

### Gestión de Base de Datos
- **Documento:** Todos (referencia cruzada)
- **Puntos clave:**
  - User-Role relationships (01)
  - Student-Carrera mappings (02)
  - Entity relationships (03)
  - Book schema (04)
  - Config parameters (05)

### Testing & Calidad
- **Documento:** 06_MODULO_QA_TESTING_HUF26-HUF30.md
- **Puntos clave:**
  - Estrategia de testing (Sección 6)
  - Coverage targets (Sección 6.2)
  - Test frameworks JUnit5/Mockito (Sección 9)
  - Pre-Go-Live checklist (Sección 12)

### APIs Externas
- **Documento:** 04_MODULO_LIBROS_HUF15-HUF20.md
- **Puntos clave:**
  - Open Library integration
  - Google Books API
  - Covers.io para portadas
  - Riesgos de timeout (Sección 7)

### Performance & Optimization
- **Documento:** 00_RESUMEN (Sección 9.3)
- **Puntos clave:**
  - Query performance targets
  - Índices de BD
  - Caching strategy
  - Load testing plans

### Riesgos & Mitigación
- **Documentos:** Todos (Sección 7 en cada uno)
- **Consolidado en:** 00_RESUMEN (Sección 7)
- **Top 5 riesgos críticos** (Sección 7.2)
- **Matriz de riesgos nivel proyecto** (Sección 7.1)

### Presupuesto & Recursos
- **Documento:** 00_RESUMEN (Sección 5)
- **Detalle por módulo:** Sección 6 de cada documento
- **Análisis EVM:** Sección 9

### Cronograma & Timeline
- **Documento:** 00_RESUMEN (Sección 6)
- **Detalle por módulo:** Sección 7 de cada módulo
- **Hitos críticos:** Sección 6.2

---

## 📈 MÉTRICAS DE SEGUIMIENTO SEMANALES

### Para PM / Stakeholders

**Checkpoints Semanales:**

```
SEMANA X:

□ Velocity tracking
  ├─ Commits completados (vs target 20-25/sprint)
  ├─ SLOC agregadas 
  └─ Burndown chart

□ Quality metrics
  ├─ Code coverage % (target 75%+)
  ├─ Bug density per KLOC
  └─ Test pass rate

□ Risk review
  ├─ Top 5 riesgos status
  ├─ Nuevos riesgos identificados
  └─ Mitigaciones en progreso

□ Budget tracking
  ├─ Actual cost vs planned
  ├─ EVM indices (SPI, CPI)
  └─ EAC vs BAC
```

**Referencia:** Secciones de KPI en cada documento módulo + EVM consolidado en 00_RESUMEN

---

## 🎯 HITOS CRÍTICOS Y GATES DE CALIDAD

| Hito | Feedback de Documentos | Target Date | Gate de Calidad |
|------|------------------------|-------------|---|
| Sprint 1 Complete | Documentación completa (6 docs) | 2026-05-11 ✅ | 8/10 funciones implementadas |
| Core Auth Ready | Doc 01 validado | 2026-05-15 | Login endpoint 100% funcional |
| All Code Complete | 6 docs validados | 2026-06-30 | 100% SLOC estimado implementado |
| Testing Complete | Doc 06 + pruebas pasan | 2026-07-15 | 75%+ coverage achieved |
| UAT Approved | Stakeholder sign-off | 2026-08-29 | Zero showstoppers |
| Go-Live Ready | Todos docs finalizados | 2026-09-01 | Prod deployment checklist ✓ |

---

## 🔔 ALERTAS CRÍTICAS (REVISAR URGENTE)

### 🔴 ROJO - Acción Inmediata Requerida

1. **TEST COVERAGE 0%** (Sección 10 del Resumen)
   - Docum relacionado: 06_MODULO_QA_TESTING.md
   - Acción: Implementar JUnit5/Mockito tests AHORA
   - Deadline: End of Sprint 2 (2026-05-26)
   - Owner: Jair + Dev leads

2. **EQUIPO INSUFICIENTE** (Sección 1.3 del Resumen)
   - Current: 1.8 FTE (solo Cesar)
   - Required: 8.0 FTE
   - Gap: -6.2 FTE
   - Acción: Contratar 2 devs junior inmediatamente
   - Budget impact: +$36K (dentro contingencia)

3. **VELOCIDAD INSOSTENIBLE** (Sección 8.1 Resumen)
   - Current: 32 commits/15 días = 2.13 c/día
   - Risk: Burnout en Cesar
   - Acción: Reducir a 1.8 c/día con equipo completo

4. **MÓDULO LIBROS CRÍTICO** (Doc 04)
   - Status: 5% completado (LENTO)
   - APIs externas: High risk
   - Acción: Acelerar o agregar dev
   - Timeline: 7.8 meses vs resto de proyecto

---

## 📞 CONTACTOS POR ÁREA

| Área | Responsable | Rol | Documento Ref |
|------|------------|-----|---|
| Autenticación | Cesar Salazar | Dev Lead | 01 |
| Estudiantes | Carlos Curo | Dev | 02 |
| Bibliotecarios | Christopher Risco | Dev | 03 |
| Libros | Nick Ronald | Dev | 04 (LENTO) |
| Config | Nipper (Ecker) | Dev | 05 |
| QA/Testing | Jair1711 | QA Lead | 06 (CRÍTICO) |
| PM/Governance | [Project Manager] | PM | 00 |
| DevOps | [DevOps Lead] | Infrastructure | 00, Sección 5.2 |
| Arquitectura | [Tech Arch] | Architecture | 00, Sección 1 |

---

## 📋 VERSIONES DE DOCUMENTOS

| Documento | Versión | Fecha | Status | Updates Esperados |
|-----------|---------|-------|--------|---|
| 00_Resumen | 1.0 | 2026-06-01 | Final | 2026-06-15 (End Sprint 2) |
| 01_Auth | 1.0 | 2026-06-01 | Final | 2026-06-15 (HUF14-15 complete) |
| 02_Estudiantes | 1.0 | 2026-06-01 | Draft | 2026-06-15 (completar) |
| 03_Bibliotecarios | 1.0 | 2026-06-01 | Draft | 2026-06-15 (completar) |
| 04_Libros | 1.0 | 2026-06-01 | Draft | 2026-06-15 (APIs testing) |
| 05_Configuración | 1.0 | 2026-06-01 | Draft | 2026-06-15 (completar) |
| 06_QA/Testing | 1.0 | 2026-06-01 | Draft | 2026-06-15 (test suite ready) |

**Próxima revisión general:** 2026-06-15 (EOW Sprint 2)

---

## ✅ CHECKLIST DE LECTURA

Marcar conforme se completa:

### Para Directivos
- [ ] Leer sección Ejecutivo (00) - 30 min
- [ ] Revisar presupuesto (Sección 5 en 00)
- [ ] Entender riesgos top 5 (Sección 7.2 en 00)
- [ ] Aprobar próximas acciones (Sección 10.1 en 00)

### Para PM
- [ ] Leer 00 completo - 60 min
- [ ] Revisar WBS en cada módulo (02-06) - 30 min cada
- [ ] Validar cronograma (Sección 6 en 00) - 20 min
- [ ] Implementar KPI tracking - 40 min
- [ ] Setup EVM dashboard - 30 min

### Para Dev Leads
- [ ] Leer docs módulos correspondientes - 45 min each
- [ ] Entender WBS y asignación de tareas
- [ ] Revisar puntos de función (scope)
- [ ] Validar estimaciones COCOMO II
- [ ] Implementar mitigación de riesgos

### Para QA Lead
- [ ] Leer 06_QA_TESTING completo - 45 min
- [ ] Revisar estrategia testing (Sección 6)
- [ ] Crear test plans para cada módulo
- [ ] Setup JUnit5/Mockito templates
- [ ] Implement CI/CD gates (75%+ coverage)

---

**Documento Generado:** 2026-06-01  
**Versión:** 1.0 FINAL  
**Total Pages:** 7 documentos markdown  
**Total Reading Time:** ~200 minutos (3.3 horas)

