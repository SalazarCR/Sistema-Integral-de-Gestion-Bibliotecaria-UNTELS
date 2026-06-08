# 📊 Estimación de Software: COCOMO II - Proyecto APIBiblioteca

## Planificación Detallada basada en Métricas del Código Fuente

---

### DIAPOSITIVA 1: PORTADA
**PROYECTO:** APIBiblioteca - Sistema Integral de Gestión Bibliotecaria  
**ORGANIZACIÓN:** Universidad Nacional Tecnológica de Lima Sur (UNTELS)  
**EQUIPO DE DESARROLLO:**
*   **Cesar Salazar:** Líder de Seguridad y JWT
*   **Jair:** Especialista en Gestión de Identidad
*   **Christopher:** Especialista en Catálogo e Integraciones
*   **Curo:** Analista de Procesos Transaccionales
*   **Nick:** Responsable de Cumplimiento y Notificaciones
*   **Nipper:** Especialista en Configuración y Documentación API

**FECHA:** 08 de Junio de 2026  
**ESTADO:** Fase de Consolidación Backend - Sprint 3

---

### DIAPOSITIVA 2: CASO PRÁCTICO (CONTEXTO REAL)

| Elemento | Descripción |
| :--- | :--- |
| **Cliente** | Biblioteca Central UNTELS |
| **Problema** | Conflicto en el control de préstamos, falta de validación de multas en tiempo real y registro manual de libros. |
| **Objetivo** | Implementar una API robusta con seguridad JWT, validación automática de sanciones y carga de libros vía ISBN. |
| **Usuarios** | Administradores, Bibliotecarios y Estudiantes (roles diferenciados en JWT). |
| **Equipo** | 6 Integrantes (Roles definidos por vertical slices). |
| **Lenguaje** | Java 17 (Backend) / TypeScript (Frontend Angular 17). |
| **Framework** | Spring Boot 4.0.5 (Hibernate, Spring Security). |
| **Base de datos** | PostgreSQL (Relacional, 6 tablas principales). |
| **Tecnologías** | Swagger UI, JWT, RestTemplate (API OpenLibrary), ModelMapper. |
| **Duración** | 12 Semanas (3 Sprints). |
| **Presupuesto** | S/. 52,200.00 (Basado en COCOMO II). |

---

### DIAPOSITIVA 3: WBS (JERARQUÍA TÉCNICA)

```text
PROYECTO: APIBIBLIOTECA
│
├── 🛡️ MODULO SEGURIDAD (Cesar)
│   ├── Configuración WebSecurity
│   └── Motor de Tokens JWT
│
├── 👤 MODULO USUARIOS (Jair)
│   ├── Gestión de Perfiles (ADMIN/USER)
│   └── Servicio de Detalles de Usuario
│
├── 📚 MODULO LIBROS (Christopher)
│   ├── Registro Manual/ISBN
│   └── Cliente API OpenLibrary
│
├── 💰 MODULO PRÉSTAMOS (Curo)
│   ├── Lógica de Solicitud/Aprobación
│   └── Control de Stock y Devoluciones
│
├── 🔔 MODULO ALERTAS (Nick)
│   ├── Cálculo de Sanciones Automáticas
│   └── Centro de Notificaciones
│
└── ⚙️ MODULO SISTEMA (Nipper)
    ├── Parámetros de Negocio (Multas/Días)
    └── Documentación Técnica Swagger
```

---

### DIAPOSITIVA 4: DEFINICIÓN DE PUNTOS DE FUNCIÓN (PF)

Los **Puntos de Función** miden la cantidad de funcionalidad entregada al usuario basándose en los componentes del sistema, independientemente de la tecnología.

*   **EI (Entradas Externas):** Formularios de registro, login, configuración.
*   **EO (Salidas Externas):** Notificaciones, reportes de multas.
*   **EQ (Consultas Externas):** Búsquedas por título, listar préstamos, Swagger UI.
*   **ILF (Archivos Lógicos Internos):** Tablas JPA (Usuario, Libro, Prestamo, Sancion, Notificacion, Config).
*   **EIF (Archivos de Interfaz Externa):** Integración con la API de OpenLibrary.

---

### DIAPOSITIVA 5: CONTEO REAL DE PUNTOS DE FUNCIÓN

Basado en el análisis de las **Entidades JPA** y **Controladores REST** del código fuente:

| Función | Tipo | Complejidad | PF Unit. | Cantidad | Total |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Entidades (Usuarios, Libros, etc.) | ILF | Media | 10 | 6 | 60 |
| Cliente API OpenLibrary | EIF | Baja | 5 | 1 | 5 |
| Registrar/Editar (CRUDs) | EI | Media | 4 | 14 | 56 |
| Notificaciones de Multa/Préstamo | EO | Baja | 4 | 2 | 8 |
| Búsquedas y Swagger | EQ | Baja | 3 | 10 | 30 |
| **TOTAL PF AJUSTADOS** | | | | | **159** |

*Nota: Se identificaron 6 ILF (tablas) y 14 EI (métodos POST/PUT en controladores).*

---

### DIAPOSITIVA 6: CONVERSIÓN PF → KSLOC (PROYECTO HÍBRIDO)

Considerando que el proyecto es un desarrollo Fullstack (Java + Angular):

| Concepto | Valor |
| :--- | :--- |
| Puntos de Función (PF) | 159 |
| Factor SLOC/PF (Java/TypeScript) | 51.5 |
| **Líneas de Código (SLOC)** | **8,188.5** |
| **KSLOC (SLOC / 1000)** | **8.19** |

*Factor:* Java (53) + TypeScript (50) / 2 = 51.5 líneas por punto de función.

---

### DIAPOSITIVA 7: ESTIMACIÓN COCOMO II (MODELO POST-ARQUITECTURA)

**Fórmulas:**
1.  **Esfuerzo (E):** $2.94 \times (KSLOC)^{1.10} \times EM$
2.  **Tiempo (T):** $3.67 \times (E)^{0.28}$
3.  **Personal (N):** $E / T$

| Métrica | Resultado |
| :--- | :--- |
| **KSLOC** | 8.19 |
| **EM (Multiplicador de Esfuerzo)** | 0.88 (Equipo Altamente Capacitado) |
| **Personas-Mes (E)** | **25.8 PM** |
| **Duración (T)** | **8.9 Meses** |
| **Equipo Óptimo (N)** | **3 Personas (Full-time)** |

*EM ajustado por: Alta capacidad del equipo (Cesar/Jair/etc), uso de herramientas (Spring/Angular) y experiencia en el dominio.*

---

### DIAPOSITIVA 8: PLAN DE RECURSOS Y PRESUPUESTO (SEMANA 5)

| Perfil | Cantidad | Costo/Mes | Meses | Subtotal |
| :--- | :--- | :--- | :--- | :--- |
| Desarrollador Backend | 3 | S/. 3,800 | 3 | S/. 34,200 |
| Desarrollador Frontend | 3 | S/. 3,500 | 2 | S/. 21,000 |
| Infraestructura Cloud | 1 | S/. 600 | 3 | S/. 1,800 |
| **COSTO TOTAL (BAC)** | | | | **S/. 57,000** |

---

### DIAPOSITIVA 9: CRONOGRAMA DE TRABAJO (GANTT)

```text
MES 1: SPRINT 1 - CORE & SEGURIDAD
[██████████] Análisis y Diseño (Todos)
[          ██████████] Seguridad JWT (Cesar)
[                    ██████████] Base de Datos (Jair)

MES 2: SPRINT 2 - LÓGICA DE NEGOCIO
[                              ██████████] Inventario (Christopher)
[                                        ██████████] Frontend Angular (Semana 6)
[                                                  ██████████] Préstamos (Curo)

MES 3: SPRINT 3 - CUMPLIMIENTO & QA
[                                                            ██████████] Sanciones (Nick)
[                                                                      ██████████] Swagger Docs (Nipper)
[                                                                                ██████████] Despliegue
```

---

### DIAPOSITIVA 10: MATRIZ DE RIESGOS ESPECÍFICOS

| ID | Riesgo | Prob. | Impacto | Nivel | Mitigación |
| :--- | :--- | :--- | :--- | :--- | :--- |
| R1 | Inestabilidad API OpenLibrary | M | Alto | Rojo | Implementar Fallback a datos locales. |
| R2 | Conflicto de puertos (8080) | A | Bajo | Verde | Configuración en application.properties. |
| R3 | Error en expiración JWT | B | Crítico | Rojo | Pruebas de integración de Cesar. |
| R4 | Retraso en componentes Angular| M | Medio | Naranja | Uso de Mock Services en el frontend. |
| R5 | Deuda técnica en Services | M | Medio | Amarillo | Code Review semanal de lógica. |
| R6 | Falta de datos de prueba | A | Bajo | Verde | Script SQL de inicialización robusto. |

---

### DIAPOSITIVA 11: KPIs DEL PROYECTO (DASHBOARD)

*   **Velocity:** 28 Puntos de Historia por Sprint.
*   **Sprint Goal Success:** 95% (Hitos cumplidos).
*   **Bug Rate:** 1.5 defectos encontrados por KSLOC en QA.
*   **Code Coverage:** 82% cobertura en servicios de préstamo y usuarios.
*   **Deployment Frequency:** 1 release por sprint (Bi-semanal).

**Gráfico Burndown:** Progreso actual alineado a la línea base de la Semana 5.

---

### DIAPOSITIVA 12: SPI (ÍNDICE DE DESEMPEÑO DEL PLAZO)

**SPI = EV (Valor Ganado) / PV (Valor Planificado)**

| Variable | Descripción | Valor |
| :--- | :--- | :--- |
| **EV** | Trabajo completado (Backend completo) | S/. 34,200 |
| **PV** | Trabajo planificado a la fecha | S/. 38,000 |

**RESULTADO SPI = 0.90**  
*Interpretación:* El proyecto tiene un ligero retraso del 10% respecto al cronograma inicial (enfoque en calidad de seguridad).

---

### DIAPOSITIVA 13: CPI (ÍNDICE DE DESEMPEÑO DEL COSTO)

**CPI = EV (Valor Ganado) / AC (Costo Real)**

| Variable | Descripción | Valor |
| :--- | :--- | :--- |
| **EV** | Trabajo completado | S/. 34,200 |
| **AC** | Gastos incurridos a la fecha | S/. 31,500 |

**RESULTADO CPI = 1.08**  
*Interpretación:* El proyecto está un 8% debajo del presupuesto planificado (Eficiencia en costos).

---

### DIAPOSITIVA 14: EAC (ESTIMACIÓN AL FINALIZAR)

**EAC = BAC / CPI**

| Métrica | Valor |
| :--- | :--- |
| **BAC (Presupuesto Base)** | S/. 57,000 |
| **CPI (Desempeño Costo)** | 1.08 |
| **EAC (Nuevo Costo Estimado)** | **S/. 52,777** |

*Conclusión:* Basado en el CPI actual, el proyecto finalizará con un costo menor al presupuesto original.

---

### DIAPOSITIVA 15: VAC (VARIACIÓN AL FINALIZAR)

**VAC = BAC - EAC**

| Métrica | Valor |
| :--- | :--- |
| **BAC** | S/. 57,000 |
| **EAC** | S/. 52,777 |
| **VAC** | **+ S/. 4,223** |

**CONCLUSIONES FINALES:**
1.  **Estado Económico:** Superávit proyectado de **S/. 4,223**.
2.  **Viabilidad:** El proyecto es altamente rentable gracias a la eficiencia en el desarrollo backend.
3.  **Acción Recomendada:** Reinvertir el superávit en optimización de rendimiento y pruebas de carga adicionales para la fase de Angular.
