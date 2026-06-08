# 📊 Planificación y Estimación de Proyecto: APIBiblioteca
## Análisis COCOMO II y Gestión de Software (Java Spring Boot + Angular)

---

### DIAPOSITIVA 1: PORTADA
**PROYECTO:** APIBiblioteca - Sistema Integral de Gestión de Biblioteca  
**ORGANIZACIÓN:** UNTELS (Ingeniería de Sistemas)  
**EQUIPO DE TRABAJO:**
*   **Cesar Salazar:** Líder de Seguridad y JWT
*   **Jair:** Gestión de Usuarios y Roles
*   **Christopher:** Catálogo e Integraciones Externas
*   **Curo:** Lógica de Préstamos y Devoluciones
*   **Nick:** Sanciones y Notificaciones
*   **Nipper:** Configuración Global y Documentación

**FECHA:** 08 de Junio de 2026  
**ESTADO:** Sprint 3 (Backend Consolidado)

---

### DIAPOSITIVA 2: CASO PRÁCTICO

| Elemento | Descripción |
| :--- | :--- |
| **Cliente** | Universidad Nacional Tecnológica de Lima Sur |
| **Problema** | Descontrol en stock de libros y falta de seguimiento automatizado de multas. |
| **Objetivo** | Digitalizar el flujo de préstamos con validación de seguridad y APIs externas. |
| **Usuarios** | Estudiantes, Bibliotecarios y Administradores. |
| **Equipo** | 6 Desarrolladores Fullstack. |
| **Lenguaje** | Java 17 / TypeScript (Angular). |
| **Framework** | Spring Boot 4.0.5 / Angular 17. |
| **Base de datos** | PostgreSQL (Relacional). |
| **Tecnologías** | JWT, OpenAPI, ModelMapper, Hibernate. |
| **Duración** | 12 Semanas (3 Sprints). |
| **Presupuesto** | S/. 52,200.00 |

---

### DIAPOSITIVA 3: WBS (WORK BREAKDOWN STRUCTURE)

```text
PROYECTO: APIBIBLIOTECA
│
├── 🛡️ MODULO SEGURIDAD (Cesar)
│   ├── Configuración WebSecurity
│   └── Motor de Tokens JWT
│
├── 👥 MODULO USUARIOS (Jair)
│   ├── Gestión de Perfiles
│   └── Servicio de Identidad
│
├── 📚 MODULO LIBROS (Christopher)
│   ├── CRUD de Libros
│   └── Cliente API OpenLibrary
│
├── 🔄 MODULO PRÉSTAMOS (Curo)
│   ├── Registro de Solicitudes
│   └── Devoluciones y Stock
│
├── 🔔 MODULO CUMPLIMIENTO (Nick)
│   ├── Gestión de Sanciones
│   └── Centro de Notificaciones
│
└── ⚙️ MODULO SISTEMA (Nipper)
    ├── Parámetros Globales
    └── Documentación Swagger
```

---

### DIAPOSITIVA 4: PUNTOS DE FUNCIÓN (PF)

Los **Puntos de Función** miden el tamaño funcional del software basándose en la interacción con el usuario y archivos lógicos.

*   **EI (External Input):** Registro de usuarios, libros, préstamos.
*   **EO (External Output):** Notificaciones de multas, reportes.
*   **EQ (External Query):** Consultas de stock, listar usuarios, Swagger.
*   **ILF (Internal Logical File):** 6 Entidades JPA (Usuario, Libro, etc.).
*   **EIF (External Interface File):** Integración con API OpenLibrary.

---

### DIAPOSITIVA 5: CONTEO DE PUNTOS DE FUNCIÓN

| Función | Tipo | Complejidad | PF Unit. | Cantidad | Total |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Entidades (ILFs) | ILF | Media | 10 | 6 | 60 |
| Integración API | EIF | Baja | 5 | 1 | 5 |
| Registrar/Editar | EI | Media | 4 | 14 | 56 |
| Alertas/Avisos | EO | Baja | 4 | 2 | 8 |
| Consultas REST | EQ | Baja | 3 | 10 | 30 |
| **TOTAL PF** | | | | | **159** |

---

### DIAPOSITIVA 6: CONVERSIÓN PF → KSLOC

| Concepto | Valor |
| :--- | :--- |
| Puntos de Función (PF) | 159 |
| Factor SLOC/PF (Promedio Java/TS) | 51.5 |
| **Líneas de Código (SLOC)** | **8,188.5** |
| **KSLOC (SLOC / 1000)** | **8.19** |

*Cálculo:* `159 PF * 51.5 (Factor medio) = 8,188.5`.

---

### DIAPOSITIVA 7: ESTIMACIÓN COCOMO II

| Métrica | Resultado |
| :--- | :--- |
| **KSLOC** | 8.19 |
| **EM (Effort Multiplier)** | 0.92 (Alta capacidad técnica) |
| **Personas-Mes (Esfuerzo)** | **26.4 PM** |
| **Duración (Meses)** | **9.0 Meses** |
| **Equipo Óptimo** | **3 Desarrolladores Full-time** |

---

### DIAPOSITIVA 8: PLAN DE RECURSOS Y PRESUPUESTO

| Perfil | Cantidad | Costo Mes | Meses | Subtotal |
| :--- | :--- | :--- | :--- | :--- |
| Desarrolladores | 3 | S/. 4,000 | 3 | S/. 36,000 |
| QA / Testing | 1 | S/. 3,500 | 2 | S/. 7,000 |
| Cloud Infra | 1 | S/. 800 | 3 | S/. 2,400 |
| Contingencia | 10% | | | S/. 4,540 |
| **TOTAL** | | | | **S/. 49,940** |

---

### DIAPOSITIVA 9: CRONOGRAMA (GANTT)

```text
SPRINT 1 (Mes 1): Core Backend
[██████████] Seguridad JWT (Cesar)
[          ██████████] Usuarios y Roles (Jair)

SPRINT 2 (Mes 2): Lógica de Negocio
[                    ██████████] Libros (Christopher)
[                              ██████████] Préstamos (Curo)
[                                        ██████████] Frontend Angular (Semana 6)

SPRINT 3 (Mes 3): Cumplimiento y Cierre
[                                                  ██████████] Sanciones (Nick)
[                                                            ██████████] QA / Swagger (Nipper)
```

---

### DIAPOSITIVA 10: MATRIZ DE RIESGOS

| ID | Riesgo | Probabilidad | Impacto | Nivel | Mitigación |
| :--- | :--- | :--- | :--- | :--- | :--- |
| R1 | Caída de API Externa | Media | Alto | Rojo | Implementar caché local. |
| R2 | Brecha de Seguridad | Baja | Crítico | Rojo | Auditoría de código en JWT. |
| R3 | Retraso en Frontend | Media | Medio | Naranja | Paralelización desde Semana 4. |
| R4 | Port Bloqueado (8080) | Alta | Bajo | Verde | Configuración dinámica. |
| R5 | Deuda Técnica | Media | Medio | Amarillo | Refactorización periódica. |
| R6 | Falta de Documentación | Baja | Bajo | Verde | Uso obligatorio de Swagger. |

---

### DIAPOSITIVA 11: KPIs DEL PROYECTO

*   **Velocity:** 26 Puntos de Historia.
*   **Code Coverage:** 85% (JUnit).
*   **Bug Rate:** < 1 defecto por KSLOC.
*   **Lead Time:** 10 días por feature.
*   **Burndown Chart:** Tendencia de cierre lineal.

---

### DIAPOSITIVA 12: SPI (ÍNDICE DE DESEMPEÑO DEL PLAZO)

**SPI = EV / PV**

| Variable | Valor |
| :--- | :--- |
| EV (Valor Ganado) | S/. 32,000 |
| PV (Valor Planificado) | S/. 34,000 |

**Resultado SPI: 0.94** (Ligero retraso cronológico).

---

### DIAPOSITIVA 13: CPI (ÍNDICE DE DESEMPEÑO DEL COSTO)

**CPI = EV / AC**

| Variable | Valor |
| :--- | :--- |
| EV (Valor Ganado) | S/. 32,000 |
| AC (Costo Real) | S/. 30,000 |

**Resultado CPI: 1.07** (Eficiencia en costos, ahorro del 7%).

---

### DIAPOSITIVA 14: EAC (ESTIMACIÓN AL FINALIZAR)

**EAC = BAC / CPI**

| Variable | Valor |
| :--- | :--- |
| BAC (Presupuesto) | S/. 49,940 |
| CPI | 1.07 |
| **EAC** | **S/. 46,672** |

---

### DIAPOSITIVA 15: VAC (VARIACIÓN AL FINALIZAR)

**VAC = BAC - EAC**

| Variable | Valor |
| :--- | :--- |
| BAC | S/. 49,940 |
| EAC | S/. 46,672 |
| **VAC** | **+ S/. 3,268** |

**CONCLUSIÓN FINAL:** El proyecto presenta un superávit de **S/. 3,268**, lo que garantiza su estabilidad económica y permite invertir en una fase de testing más exhaustiva.
