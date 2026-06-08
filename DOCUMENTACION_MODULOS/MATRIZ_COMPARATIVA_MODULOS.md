# MATRIZ COMPARATIVA CONSOLIDADA - TODOS LOS MÓDULOS

## Comparación Visual Rápida de los 6 Módulos

**Actualizado:** 2026-06-01  
**Período:** Sprint 1 (2026-04-27 a 2026-05-11)

---

## 1. MATRIZ GENERAL DE MÉTRICAS

```
┌──────────────────┬────────────┬─────────┬──────────┬────────────┬────────────┬────────────────────┐
│ MÓDULO           │ COMMITS    │ SLOC    │ PF       │ PRESUPUESTO│ DURACIÓN   │ EQUIPO ÓPTIMO     │
├──────────────────┼────────────┼─────────┼──────────┼────────────┼────────────┼────────────────────┤
│ 1. AUTENTICACIÓN │ 32 (62.7%) │ 280     │ 104      │ $104,422   │ 9 meses    │ 3 personas (Lead)  │
│ 2. ESTUDIANTES   │ 4  (7.8%)  │ 140     │ 56       │ $18,181    │ 7.3 meses  │ 1.5 personas       │
│ 3. BIBLIOTECARIOS│ 5  (9.8%)  │ 200     │ 32       │ $12,443    │ 5.5 meses  │ 1.1 personas       │
│ 4. LIBROS        │ 2  (3.9%)  │ 150     │ 84       │ $33,758    │ 7.8 meses  │ 1.8 personas       │
│ 5. CONFIGURACIÓN │ 2  (3.9%)  │ 120     │ 38       │ $11,629    │ 5.7 meses  │ 1.1 personas       │
│ 6. QA/TESTING    │ 4  (7.8%)  │ 200     │ 74       │ $15,771    │ 6.9 meses  │ 1.4 personas       │
├──────────────────┼────────────┼─────────┼──────────┼────────────┼────────────┼────────────────────┤
│ TOTAL/PROMEDIO   │ 51 (100%)  │ 1,090   │ 388      │ $196,204*  │ 7.0 meses  │ 1.65 FTE avg       │
└──────────────────┴────────────┴─────────┴──────────┴────────────┴────────────┴────────────────────┘

*Sin overhead (PM, DevOps, Arqitectura) = $55K adicional
Presupuesto Total del Proyecto: $251,123 USD
```

---

## 2. ESTADO DE AVANCE POR MÓDULO

```
MÓDULO                    % CÓDIGO    % TIEMPO    % PF    SALUD
────────────────────────────────────────────────────────────────
1. AUTENTICACIÓN      ████████░░ 80%     50%    100%    ✅ BUENO
2. ESTUDIANTES        ██░░░░░░░░ 20%     50%     30%    🟡 ALERTA
3. BIBLIOTECARIOS     ██░░░░░░░░ 20%     50%     30%    🟡 ALERTA
4. LIBROS             █░░░░░░░░░  5%     50%      5%    🔴 CRÍTICA
5. CONFIGURACIÓN      █░░░░░░░░░  5%     50%      5%    🔴 CRÍTICA
6. QA/TESTING         █░░░░░░░░░  5%     50%      5%    🔴 CRÍTICA

PROYECTO TOTAL        ███░░░░░░░ 30%     50%     30%    🟡 EN TRANSICIÓN
```

---

## 3. TABLA COMPARATIVA DETALLADA

| Aspecto | Auth | Estud | Biblio | Libros | Config | QA |
|---------|------|-------|--------|--------|--------|-----|
| **Desarrollador** | Cesar | Curo | Christopher | Nick | Nipper | Jair |
| **Commits** | 32 | 4 | 5 | 2 | 2 | 4 |
| **Velocidad (c/día)** | 2.13 | 0.27 | 0.33 | 0.13 | 0.13 | 0.27 |
| **SLOC Actual** | 280 | 140 | 200 | 150 | 120 | 200 |
| **PF Estimado** | 104 | 56 | 32 | 84 | 38 | 74 |
| **SLOC Proyectado** | 5,720 | 3,080 | 1,760 | 4,620 | 2,090 | 2,960 |
| **Esfuerzo (PM)** | 22 | 11 | 6 | 14.2 | 6.5 | 9.8 |
| **Duración (meses)** | 9 | 7.3 | 5.5 | 7.8 | 5.7 | 6.9 |
| **Equipo (FTE)** | 3 | 1.5 | 1.1 | 1.8 | 1.1 | 1.4 |
| **Presupuesto** | $104.4K | $18.2K | $12.4K | $33.8K | $11.6K | $15.8K |
| **Completitud %** | 50% | 20% | 20% | 5% | 5% | 5% |
| **Code Coverage** | 0% | 0% | 0% | 0% | 0% | 0% |
| **Bug Density** | 7/KLOC | - | - | - | - | - |
| **Riesgos Críticos** | 2 | 4 | 3 | 5 | 3 | 4 |
| **Status General** | 🟢 OK | 🟡 WATCH | 🟡 WATCH | 🔴 LENTO | 🔴 LENTO | 🔴 TODO |

---

## 4. DISTRIBUCIÓN DE PUNTOS DE FUNCIÓN

```
DESGLOSE DE 388 PF TOTALES:

                    EI      EO      EQ      ILF     EIF    TESTING
                    ──      ──      ──      ───     ───    ───────
1. AUTENTICACIÓN    16      20      12      37      19     0       = 104 PF (27%)
2. ESTUDIANTES      14      9       9       17      7      0       = 56 PF (14%)
3. BIBLIOTECARIOS   10      8       4       10      0      0       = 32 PF (8%)
4. LIBROS           16      14      10      29      15     0       = 84 PF (21%)
5. CONFIGURACIÓN    11      7       6       14      0      0       = 38 PF (10%)
6. QA/TESTING       0       0       0       0       0      74      = 74 PF (19%)
────────────────────────────────────────────────────────────────────────
TOTAL               69      58      41      107     41     74      = 388 PF

Proporción:
EI:EO:EQ:ILF:EIF:Testing = 18%:15%:11%:27%:11%:19%

Insights:
✓ 27% en almacenamiento (ILF) = Proyecto intensivo en datos
✓ 18% en inputs, 15% en outputs = Balance EI/EO ✓
✓ 19% en testing = Adecuado para sistemas críticos
```

---

## 5. LÍNEAS DE CÓDIGO POR MÓDULO

```
SLOC PROYECTADO (20,230 total):

                 Actual    Proyectado    % Completado    SLOC/PF
─────────────────────────────────────────────────────────────
AUTH             280       5,720         4.9%           55
ESTUD            140       3,080         4.5%           55
BIBLIO           200       1,760         11.4%          55
LIBROS           150       4,620         3.2%           55
CONFIG           120       2,090         5.7%           55
QA/TEST          200       2,960         6.8%           40 (tests)
─────────────────────────────────────────────────────────────
TOTAL            1,090     20,230        5.4%           52.1

⚠️ Proyección: A ritmo actual de 1,090 SLOC en 15 días:
   - Full project en 190 días = 6.3 meses (vs 9 planeados)
   - Velocidad 115% sobre lo planeado
   - RIESGO: Burnout + calidad comprometida
```

---

## 6. PRESUPUESTO COMPARATIVO POR MÓDULO

```
PRESUPUESTO TOTAL: $251,123 USD

Desglose RRHH (82%):
┌─────────────────────────────────────────────────────────┐
│ Auth:        $91,800 (36%)  ████████████████████░░░░░░  │
│ Libros:      $24,300 (9%)   ████░░░░░░░░░░░░░░░░░░░░░░  │
│ Estudiantes: $15,915 (6%)   ███░░░░░░░░░░░░░░░░░░░░░░░  │
│ QA/Test:     $11,520 (4%)   ██░░░░░░░░░░░░░░░░░░░░░░░░  │
│ Biblio:      $11,000 (4%)   ██░░░░░░░░░░░░░░░░░░░░░░░░  │
│ Config:      $9,975 (4%)    ██░░░░░░░░░░░░░░░░░░░░░░░░  │
│ Overhead:    $42,000 (17%)  ████████░░░░░░░░░░░░░░░░░░  │
└─────────────────────────────────────────────────────────┘

Costo por Unidad:
┌─────────────────────────────────────────────┐
│ Costo / KLOC:     $12.41/línea               │
│ Costo / PF:       $647/PF                    │
│ Costo / Mes:      $27,903/mes                │
│ Costo / PM:       $3,476/persona-mes        │
└─────────────────────────────────────────────┘
```

---

## 7. CRONOGRAMA COMPARATIVO

```
SPRINT ASSIGNMENT:

SPRINT      MES        MÓDULOS ACTIVOS                      HITO
─────────────────────────────────────────────────────────────────
1 (50%)     Mayo 1-15  AUTH ████ ESTUD ██ BIBLIO ██ LIBROS █  Kickoff + Auth
2 (0%)      Mayo 16-30 AUTH ██ ESTUD ████ BIBLIO ████ LIBROS ██ Config ██ QA prep
3 (0%)      Jun 1-15   ESTUD ██ BIBLIO ██ LIBROS ████ CONFIG ████  Core Feature Complete
4 (0%)      Jun 16-30  LIBROS ████ QA ████                      APIs Integrated
5-6 (0%)    Jul 1-31   QA ████████  ALL ████ Refinement      Testing Phase
7 (0%)      Aug 1-31   UAT ████████  ALL ████                UAT Ready
8 (0%)      Sep 1-15   GO-LIVE ████████                       🚀 Production
9 (0%)      Sep 16-22  Monitor ████  Hotfixes ██             Stabilization

VELOCIDAD POR MÓDULO:
┌────────────────────────────────────────────────────────┐
│ Auth:       ████████░░ 2.13 commits/día (RÁPIDO)      │
│ Estud:      ██░░░░░░░░ 0.27 commits/día               │
│ Biblio:     ██░░░░░░░░ 0.33 commits/día               │
│ Libros:     █░░░░░░░░░ 0.13 commits/día  (LENTO)      │
│ Config:     █░░░░░░░░░ 0.13 commits/día  (LENTO)      │
│ QA/Test:    ██░░░░░░░░ 0.27 commits/día               │
└────────────────────────────────────────────────────────┘
```

---

## 8. MATRIZ DE RIESGOS COMPARATIVA

```
RIESGOS POR MÓDULO:

TYPE               AUTH    ESTUD   BIBLIO  LIBROS  CONFIG  QA/TEST
─────────────────────────────────────────────────────────────────
Velocidad          🔴      🟡      🟡      🟠      🟠      🟡
Performance        🟠      🟡      🟡      🔴      🟢      🟠
Seguridad          🔴      🟡      🟡      🟠      🟢      🟡
Integración        🟠      🟠      🟠      🔴      🟡      🟢
Testing            🔴      🔴      🔴      🔴      🔴      🟠
Scope Creep        🟡      🟡      🟡      🟠      🟡      🟡
API External       🟢      🟢      🟢      🔴      🟢      🟢
Recursos           🔴      🟠      🟠      💗      🟠      🟠
─────────────────────────────────────────────────────────────────
Riesgos Críticos   2/8     4/8     3/8     5/8     3/8     4/8

Legend: 🔴=Crítico  🟠=Alto  🟡=Medio  🟢=Bajo
```

---

## 9. DEPENDENCIAS ENTRE MÓDULOS

```
MAPA DE DEPENDENCIAS:

                    ┌──────────────┐
                    │   AUTH       │
                    │  (CORE)      │
                    └────────┬─────┘
                         ┌───┴────┬──────────┬──────────┐
                         ↓        ↓          ↓          ↓
                    ┌────────┐ ┌────────┐ ┌────────┐ ┌──────────┐
                    │ESTUD   │ │BIBLIO  │ │CONFIG  │ │LIBROS    │
                    │(Users) │ │(Users) │ │(Global)│ │(Books)   │
                    └────┬───┘ └───┬────┘ └────┬───┘ └────┬─────┘
                         │         │           │          │
                         ├─────────┼───────────┼──────────┤
                         │         │           │          │
                         ↓         ↓           ↓          ↓
                    ┌─────────────────────────────────────────┐
                    │           QA / TESTING                  │
                    │     (Integration testing)               │
                    └─────────────────────────────────────────┘

Criticidad:
✅ AUTH: Blocker → Todo depende aquí
⚠️  LIBROS: APIs externas → Alto riesgo
✓ ESTUD, BIBLIO: Mediano
✓ CONFIG: Independiente
✓ QA: Depende de todos

Camino Crítico:
AUTH (completo) → ESTUD+BIBLIO → LIBROS → QA → Go-Live
≈ 9 meses si en paralelo
```

---

## 10. ANÁLISIS EVM CONSOLIDADO

```
COMPARATIVA EVM POR MÓDULO (Período Sprint 1):

MÓDULO              PV        EV        AC        SPI     CPI
──────────────────────────────────────────────────────────────
AUTH              $5,806    $83,538    $2,742   14.39   30.48
ESTUD             $1,018    $1,530     $900     1.50    1.70
BIBLIO            $697      $931       $700     1.33    1.33
LIBROS            $1,890    $1,890     $800     1.00    2.36
CONFIG            $651      $651       $650     1.00    1.00
QA/TEST           $882      $882       $708     1.00    1.24
──────────────────────────────────────────────────────────────
TOTAL             $11,944   $89,422    $6,500   7.49    13.76

Interpretación:
✅ Todos módulos muestran SPI > 1 (adelanto)
⚠️  Auth domina EV por commits altos
🟡 CPI > 10 es FALSO positivo (solo 1 dev = bajo AC)
🔮 EAC realista cuando equipo completo: BAC = $251K
```

---

## 11. SCORECARD GENERAL POR MÓDULO

```
RATING: ⭐⭐⭐⭐⭐ = Excelente | ⭐⭐⭐⭐ = Bueno | ⭐⭐⭐ = Aceptable
        ⭐⭐ = Bajo | ⭐ = Crítico

MÓDULO          CÓDIGO    PROGRESO  CALIDAD   RIESGOS   SCHEDULE  OVERALL
────────────────────────────────────────────────────────────────────────
1. AUTH         ⭐⭐⭐⭐   ⭐⭐⭐⭐   ⭐⭐      ⭐⭐      ⭐⭐⭐    ⭐⭐⭐
2. ESTUD        ⭐⭐      ⭐⭐      ⭐⭐      ⭐⭐      ⭐⭐⭐    ⭐⭐
3. BIBLIO       ⭐⭐      ⭐⭐      ⭐⭐      ⭐⭐      ⭐⭐⭐    ⭐⭐
4. LIBROS       ⭐        ⭐        ⭐⭐      ⭐        ⭐⭐      ⭐
5. CONFIG       ⭐        ⭐⭐      ⭐⭐      ⭐⭐      ⭐⭐      ⭐
6. QA/TEST      ⭐        ⭐        ⭐        ⭐        ⭐        ⭐
────────────────────────────────────────────────────────────────────────
PROYECTO        ⭐⭐      ⭐⭐      ⭐⭐      ⭐⭐      ⭐⭐      ⭐⭐

Recomendación: 🟡 ATENCIÓN REQUERIDA - Acelerar módulos lento & Testing
```

---

## 12. TOP 10 ACCIONES INMEDIATAS

Prioridad por módulo:

| # | Acción | Módulo | Owner | Deadline | Impact |
|---|--------|--------|-------|----------|--------|
| 1 | Agregar 2 devs junior | ALL | PM | 2026-06-10 | 🔴 CRÍTICA |
| 2 | Implement CI/CD coverage gates | QA | QA Lead | 2026-06-01 | 🔴 CRÍTICA |
| 3 | Acelerar Libros (APIs) | LIBROS | Nick | 2026-06-15 | 🟠 ALTA |
| 4 | Validar DB schema | ESTUD/BIBLIO | Arqt | 2026-06-05 | 🟠 ALTA |
| 5 | Test suite template | QA | Jair | 2026-06-08 | 🟠 ALTA |
| 6 | Scope control formal | LIBROS | PM | 2026-06-01 | 🟠 ALTA |
| 7 | Circuit breaker APIs | LIBROS | Dev | 2026-06-15 | 🟡 MEDIA |
| 8 | Performance baseline | ALL | QA | 2026-06-20 | 🟡 MEDIA |
| 9 | Documentation update | ALL | PMs | Ongoing | 🟡 MEDIA |
| 10 | Cross-training sessions | ALL | Cesar | 2026-06-08 | 🟡 MEDIA |

---

## 13. CONCLUSIONES COMPARATIVAS

### Fortalezas
✅ **Módulo Autenticación:** Excelente progreso (50%), arquitectura sólida, lead exp
✅ **Velocidad inicial:** 51 commits en 15 días = momentum fuerte
✅ **Documentación:** Completa en todos módulos

### Debilidades Críticas
🔴 **Testing:** 0% coverage en TODOS módulos (blocker for go-live)
🔴 **Equipos lentos:** Libros (2 c/15d), Config (2 c/15d)
🔴 **Recursos:** 1.8 FTE actual vs 8 FTE requeridos

### Oportunidades
🟢 **Parallelización:** 5 módulos pueden desarrollarse concurrentemente
🟢 **Ramp-up rápido:** Documentación facilita onboarding nuevos devs
🟢 **Budget cushion:** Ahorros proyectados de $10K

### Amenazas
🔴 **Scope creep:** Cliente pidiendo features post-kickoff
🔴 **API externas:** Open Library, Covers.io unreliability
🔴 **Schedule:** Gap de 6.2 FTE si no se contrata rápido

---

**Documento Generado:** 2026-06-01  
**Datos:** 51 commits | 1,399 SLOC | 388 PF | $251,123 USD  
**Status:** 🟡 AMARILLA - Requiere atención inmediata en riesgos críticos

