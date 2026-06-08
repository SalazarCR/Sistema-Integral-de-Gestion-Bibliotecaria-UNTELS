# 📊 Planificación y Gestión de Proyecto: APIBiblioteca
## Estimación COCOMO II y Plan de Gestión de Software

---

### DIAPOSITIVA 1: PORTADA
**PROYECTO:** Sistema de Gestión de Biblioteca Universitaria (APIBiblioteca)  
**ORGANIZACIÓN:** UNTELS - Facultad de Ingeniería  
**INTEGRANTES:**  
*   Cesar Salazar (Seguridad & JWT)
*   Jair (Gestión de Usuarios)
*   Christopher (Catálogo de Libros)
*   Curo (Gestión de Préstamos)
*   Nick (Sanciones y Notificaciones)
*   Nipper (Configuración y Documentación)

**FECHA:** 07 de Junio de 2026  
**CURSO:** Gestión de Proyectos de Software  

---

### DIAPOSITIVA 2: CASO PRÁCTICO

| Elemento | Descripción |
| :--- | :--- |
| **Cliente** | Universidad Nacional Tecnológica de Lima Sur (UNTELS) |
| **Problema** | Gestión manual de préstamos, falta de control de stock y sanciones ineficientes. |
| **Objetivo** | Automatizar el flujo de préstamos y devoluciones con seguridad JWT y catálogo externo. |
| **Usuarios** | Administradores, Bibliotecarios y Estudiantes. |
| **Equipo** | 6 Desarrolladores Fullstack. |
| **Lenguaje** | Java (Backend) / TypeScript (Frontend). |
| **Framework** | Spring Boot 4.0.5 / Angular 17. |
| **Base de datos** | PostgreSQL 16. |
| **Tecnologías** | JWT, OpenAPI/Swagger, ModelMapper, Hibernate. |
| **Duración estimada** | 12 semanas (3 Sprints). |
| **Presupuesto preliminar** | S/. 45,000.00 |

---

### DIAPOSITIVA 3: WBS (WORK BREAKDOWN STRUCTURE)

```text
PROYECTO: APIBIBLIOTECA
│
├── 🔑 GESTIÓN DE SEGURIDAD (Cesar)
│   ├── Autenticación JWT
│   └── Autorización de Rutas (RBAC)
│
├── 👥 GESTIÓN DE USUARIOS (Jair)
│   ├── Registro de Estudiantes/Personal
│   └── Gestión de Roles y Estados
│
├── 📚 CATÁLOGO DE LIBROS (Christopher)
│   ├── CRUD de Libros
│   └── Sincronización OpenLibrary API
│
├── 🔄 GESTIÓN DE PRÉSTAMOS (Curo)
│   ├── Registro de Solicitudes
│   └── Control de Devoluciones y Stock
│
├── ⚠️ CUMPLIMIENTO (Nick)
│   ├── Gestión de Sanciones/Multas
│   └── Sistema de Notificaciones
│
└── ⚙️ ADMINISTRACIÓN (Nipper)
    ├── Parámetros Globales
    └── Documentación Swagger/OpenAPI
```

---

### DIAPOSITIVA 4: PUNTOS DE FUNCIÓN (DEFINICIONES)

| Sigla | Nombre | Descripción |
| :--- | :--- | :--- |
| **EI** | External Input | Datos que entran al sistema (Formularios, Logins). |
| **EO** | External Output | Datos que salen (Reportes, Notificaciones). |
| **EQ** | External Query | Consultas interactivas que no modifican datos. |
| **ILF** | Internal Logical File | Grupos de datos mantenidos internamente (Entidades JPA). |
| **EIF** | External Interface File | Datos mantenidos por otros sistemas (API OpenLibrary). |

**¿Qué son los Puntos de Función?** Es una métrica estándar para medir el tamaño funcional de un software basándose en lo que el usuario recibe, independientemente de la tecnología.

---

### DIAPOSITIVA 5: CONTEO DE PUNTOS DE FUNCIÓN

| Función | Tipo | Complejidad | PF Unitario | Cantidad | Total PF |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Entidades (Usuarios, Libros, etc.) | ILF | Media | 10 | 6 | 60 |
| API OpenLibrary | EIF | Baja | 5 | 1 | 5 |
| Registro/Edición (CRUD) | EI | Media | 4 | 12 | 48 |
| Notificaciones/Alertas | EO | Baja | 4 | 2 | 8 |
| Búsquedas y Listados | EQ | Baja | 3 | 10 | 30 |
| **TOTAL PUNTOS DE FUNCIÓN** | | | | | **151** |

---

### DIAPOSITIVA 6: CONVERSIÓN PF → KSLOC

Utilizamos el factor de conversión para un proyecto híbrido entre Java y TypeScript (Angular).

| Concepto | Valor |
| :--- | :--- |
| Puntos de Función (PF) | 151 |
| Factor SLOC/PF (Promedio Java/TS) | 51.5 |
| **SLOC Totales** | **7,776.5** |
| **KSLOC (Líneas de Código / 1000)** | **7.78** |

*Cálculo:* `SLOC = 151 PF * 51.5 (Factor medio) = 7,776.5`.

---

### DIAPOSITIVA 7: ESTIMACIÓN COCOMO II

| Métrica | Resultado |
| :--- | :--- |
| **PF** | 151 |
| **KSLOC** | 7.78 |
| **EM (Effort Multiplier)** | 1.15 (Complejidad nominal+) |
| **Personas-Mes (Esfuerzo)** | **27.6 PM** |
| **Duración (T)** | **9.1 Meses** |
| **Equipo Óptimo** | **3 Personas** |

*Fórmulas Aplicadas:*  
*   $E = 2.94 \times (7.78)^{1.10} \times 1.15 \approx 27.6$ PM.
*   $T = 3.67 \times (27.6)^{0.28} \approx 9.1$ meses.
*   $N = 27.6 / 9.1 \approx 3$ integrantes estables.

---

### DIAPOSITIVA 8: PLAN DE RECURSOS Y PRESUPUESTO

| Perfil | Cantidad | Costo Mes | Meses | Subtotal |
| :--- | :--- | :--- | :--- | :--- |
| Desarrollador Backend | 3 | S/. 3,500 | 3 | S/. 31,500 |
| Desarrollador Frontend | 3 | S/. 3,200 | 2 | S/. 19,200 |
| Infraestructura (Cloud) | 1 | S/. 500 | 3 | S/. 1,500 |
| **TOTAL ESTIMADO** | | | | **S/. 52,200** |

---

### DIAPOSITIVA 9: CRONOGRAMA (GANTT)

```text
SPRINT 1 (Mes 1): Planificación y Seguridad
[██████████] Planificación
[          ██████████] Diseño de BD
[                    ██████████] Seguridad JWT (Cesar)

SPRINT 2 (Mes 2): Módulos Base y Lógica
[                              ██████████] Usuarios (Jair)
[                                        ██████████] Libros (Christopher)
[                                                  ██████████] Frontend Angular (Semana 6)

SPRINT 3 (Mes 3): Transacciones y Despliegue
[                                                            ██████████] Préstamos (Curo)
[                                                                      ██████████] Sanciones (Nick)
[                                                                                ██████████] Testing/UAT
```

---

### DIAPOSITIVA 10: MATRIZ DE RIESGOS

| ID | Riesgo | Probabilidad | Impacto | Nivel | Mitigación |
| :--- | :--- | :--- | :--- | :--- | :--- |
| R1 | Cambio de requerimientos | Alta | Medio | Naranja | Control estricto de Backlog. |
| R2 | Vulnerabilidad en JWT | Baja | Crítico | Rojo | Auditoría de Cesar y pruebas de estrés. |
| R3 | Caída de API OpenLibrary | Media | Bajo | Amarillo | Implementar caché local de metadatos. |
| R4 | Retraso en Frontend Angular| Media | Medio | Naranja | Paralelizar tareas desde la Semana 4. |
| R5 | Deuda técnica por rapidez | Media | Medio | Amarillo | Code Reviews semanales. |
| R6 | Conflicto de puertos (8080) | Alta | Bajo | Verde | Configuración dinámica de perfiles. |

---

### DIAPOSITIVA 11: KPIs DEL PROYECTO

| KPI | Valor Objetivo |
| :--- | :--- |
| **Velocity** | 25 Puntos/Sprint |
| **Sprint Goal Success** | > 90% |
| **Bug Rate** | < 2 por KSLOC |
| **Code Coverage** | > 80% (JUnit) |
| **Deployment Frequency** | 1 por semana |

**Burndown Chart:** Tendencia negativa constante (progreso óptimo).

---

### DIAPOSITIVA 12: SPI (SCHEDULE PERFORMANCE INDEX)

**Fórmula:** $SPI = EV / PV$

| Variable | Descripción | Valor Estimado |
| :--- | :--- | :--- |
| **EV** | Earned Value (Valor ganado) | S/. 30,000 |
| **PV** | Planned Value (Valor planificado)| S/. 32,000 |

**Resultado SPI: 0.94**  
*Interpretación:*  
*   SPI < 1: Retraso respecto al cronograma (Actual).  
*   SPI = 1: Según lo planificado.  
*   SPI > 1: Adelantado.

---

### DIAPOSITIVA 13: CPI (COST PERFORMANCE INDEX)

**Fórmula:** $CPI = EV / AC$

| Variable | Descripción | Valor Estimado |
| :--- | :--- | :--- |
| **EV** | Earned Value (Valor ganado) | S/. 30,000 |
| **AC** | Actual Cost (Costo real) | S/. 28,500 |

**Resultado CPI: 1.05**  
*Interpretación:*  
*   CPI < 1: Sobre el presupuesto.  
*   CPI = 1: Según presupuesto.  
*   **CPI > 1: Debajo del presupuesto (Ahorro).**

---

### DIAPOSITIVA 14: EAC (ESTIMATE AT COMPLETION)

**Fórmula:** $EAC = BAC / CPI$

| Métrica | Valor |
| :--- | :--- |
| **BAC** (Budget at Completion) | S/. 52,200 |
| **CPI** (Índice de Costo) | 1.05 |
| **EAC** (Costo Final Estimado) | **S/. 49,714** |

*Conclusión:* El proyecto terminará costando menos de lo presupuestado originalmente debido a la eficiencia del equipo.

---

### DIAPOSITIVA 15: VAC (VARIANCE AT COMPLETION)

**Fórmula:** $VAC = BAC - EAC$

| Métrica | Valor |
| :--- | :--- |
| **BAC** | S/. 52,200 |
| **EAC** | S/. 49,714 |
| **VAC** | **+ S/. 2,486** |

**Interpretación Final:**  
*   **VAC > 0 (Positivo):** El proyecto tiene un superávit de S/. 2,486.
*   El proyecto es económicamente viable y saludable. A pesar de un ligero retraso cronológico (SPI 0.94), la gestión de recursos financieros es excelente (CPI 1.05).
