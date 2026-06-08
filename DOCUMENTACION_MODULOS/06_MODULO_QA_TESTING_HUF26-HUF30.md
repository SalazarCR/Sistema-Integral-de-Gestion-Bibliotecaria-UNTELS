# DOCUMENTACIÓN COMPLETA DEL MÓDULO DE TESTING E INTEGRACIÓN (HUF26-HUF30)

## Sistema Integral de Gestión de Biblioteca Académica Universitaria (SIGBAU)

**Fecha de Generación:** 2026-06-01  
**Desarrollador Principal:** Jair1711  
**Commits Asignados:** 4  
**SLOC Asignado:** ~200 líneas (tests)  
**Período:** 2026-04-27 a 2026-05-11 (participación inicial QA prep)

---

## 1. CASO PRÁCTICO DEL MÓDULO

| Aspecto | Descripción |
|--------|-------------|
| **Nombre** | Módulo de QA, Testing e Integración |
| **Código** | HUF26-HUF30 |
| **Objetivo** | Testing automatizado, integración continua, UAT, preparación Go-Live |
| **Stack** | JUnit5, Mockito, Selenium, Jest, Cypress |
| **Ejecución** | Paralela a desarrollo + final UAT |

### Funcionalidades

**HUF26:** Unit Testing Backend
- JUnit5 + Mockito tests
- 75%+ code coverage
- Tests para servicios, controllers, repositories

**HUF27:** Integration Testing
- Tests E2E de flujos
- Docker Compose para test DB
- API-level testing

**HUF28:** Frontend Testing
- Jest unit tests (React)
- Cypress E2E tests
- Visual regression tests

**HUF29:** Performance Testing
- Load testing (Apache JMeter)
- Stress testing
- Memory profiling

**HUF30:** UAT + Go-Live
- User Acceptance Testing
- Production readiness checks
- Deployment runbook

---

## 2. WBS

```
HUF26-HUF30: QA & INTEGRACIÓN
├─ HUF26: Unit Tests
│  ├─ AuthServiceTest
│  ├─ StudentServiceTest
│  ├─ LibrarianServiceTest
│  ├─ BookServiceTest
│  ├─ ConfigParametroServiceTest
│  └─ Coverage reports (target 75%)
├─ HUF27: Integration Tests
│  ├─ E2E Auth Flow
│  ├─ E2E Student Registration
│  ├─ E2E Book Search
│  ├─ Data consistency tests
│  └─ Database integrity checks
├─ HUF28: Frontend Tests (Future Sprint)
│  ├─ Component Jest tests
│  ├─ Cypress E2E scenarios
│  └─ Visual regression (Percy.io)
├─ HUF29: Performance Tests
│  ├─ Login endpoint load (100 req/s)
│  ├─ Search endpoint load (50 req/s)
│  ├─ Memory profiling
│  └─ DB query optimization
└─ HUF30: UAT Preparation
   ├─ Acceptance criteria validation
   ├─ Security audit checklist
   ├─ Deployment playbook
   └─ Rollback procedures
```

---

## 3. ESTIMACIÓN POR PUNTOS DE FUNCIÓN

**Nota:** Testing no genera funcionalidad de usuario (no EI/EO)  
Pero es **función de soporte crítica** que afecta confiabilidad

| Tipo | Función | Complejidad | PF | Total |
|------|---------|-------------|-------|--------|
| Testing Infrastructure | Test framework setup | Media | 5 | 5 |
| Test Suite 1 | Auth module tests | Media | 8 | 8 |
| Test Suite 2 | Student module tests | Media | 8 | 8 |
| Test Suite 3 | Librarian module tests | Media | 8 | 8 |
| Test Suite 4 | Book module tests | Alta | 10 | 10 |
| Test Suite 5 | Config module tests | Baja | 5 | 5 |
| Integration Tests | E2E scenarios | Alta | 12 | 12 |
| Performance Tests | Load + stress testing | Alta | 10 | 10 |
| UAT Preparation | Test cases + docs | Media | 8 | 8 |
| **Approx Total QA PF** | | | | **74 PF** |

```
SLOC = 74 × 40 = 2,960 líneas (tests son más concisos)
KLOC = 2.96

E = 2.94 × (2.96)^1.10 = 9.8 PM
TDEV = 3.67 × (9.8)^0.28 = 6.9 meses
Equipo: 1.4 personas  
(1 QA engineer + 0.4 dev support)
```

---

## 4. PRESUPUESTO

| Concepto | Monto |
|----------|-------|
| QA Engineer/Tester | $7,200 (1 dev × 6.9 months) |
| Dev Support (testing) | $4,320 (0.4 dev × 6.9 months) |
| Testing Tools | $1,200 (Junit, Mockito, Cypress) |
| Performance Tools | $800 (JMeter, New Relic) |
| Infrastructure (test DB) | $1,500 |
| **Subtotal** | $15,020 |
| Contingency (5%) | $751 |
| **Total QA Module** | $15,771 |

---

## 5. CRONOGRAMA

| Fase | Sprint | Período | Actividad | Status |
|------|--------|---------|----------|--------|
| **Phase 1** | 1-2 | May | Setup test infrastructure, write unit tests | ⏳ START |
| **Phase 2** | 3 | June 1-15 | Integration testing, fix bugs | ⏳ PENDING |
| **Phase 3** | 4 | June 16-30 | Performance testing | ⏳ PENDING |
| **Phase 4** | 5 | July | UAT preparation | ⏳ PENDING |
| **Phase 5** | 6-7 | Aug-Sep | Final QA + Go-Live prep | ⏳ PENDING |

---

## 6. ESTRATEGIA DE TESTING

### Pirámide de Testing

```
        /\           E2E Tests (~10%)
       /  \          - Selenium / Cypress
      /____\         - End-to-end flows
     /      \        
    / INTEG /        Integration Tests (~20-30%)
   /________\        - API level
   /        \        - Database level
  / UNIT    /        - Module interactions
 /_________/         
    Unit Tests       Unit Tests (~60-70%)
    - AuthService    - Service layer
    - StudentService - Controllers
    - All repos      - DTOs
```

### Test Coverage Targets

| Component | Unit | Integration | E2E | Total Target |
|-----------|------|-------------|-----|--------------|
| Controllers | 70% | 90% | - | 75%+ |
| Services | 85% | 80% | - | 85%+ |
| Repositories | 60% | 95% | - | 70%+ |
| DTOs | 50% | - | - | 50%+ |
| **Overall** | | | | **75%+** |

---

## 7. MATRIZ DE RIESGOS

| Riesgo | Prob | Impacto | Mitigación |
|--------|------|---------|-----------|
| Low test coverage | 40% | Alto | Enforce gates, automate coverage checks |
| Flaky tests | 30% | Medio | Timeout config, retry logic |
| Performance regression | 35% | Alto | Baseline metrics, trend analysis |
| UAT blocker bugs | 25% | Crítico | Proper prioritization framework |
| Test data corruption | 20% | Medio | Isolation + cleanup each test |

---

## 8. KPIs ESPECÍFICOS QA

| Métrica | Fórmula | Target | Actual |
|---------|---------|--------|--------|
| Code Coverage | (Lines tested / Total lines) × 100 | 75% | 0% (pending) |
| Test Pass Rate | (Passed tests / Total tests) × 100 | 95% | - |
| Bug Escape Rate | (Production bugs / Total bugs) × 100 | <5% | - |
| Test Execution Time | Total test runtime | <5 min | - |
| Test Case Effectiveness | (Bugs found / Test cases) × 100 | >10% | - |

---

## 9. HERRAMIENTAS Y FRAMEWORKS

### Backend Testing

```java
// JUnit 5
@Test
@DisplayName("Auth login successful with valid credentials")
void loginSuccess() { ... }

// Mockito
@Mock
IUserRepository userRepository;

@InjectMocks
AuthServiceImplement authService;

// AssertJ
assertThat(result).isNotNull().hasFieldOrProperty("token");
```

### Frontend Testing (React)

```javascript
// Jest
describe('LoginComponent', () => {
  it('should render login form', () => { ... });
});

// Cypress
cy.visit('/login');
cy.get('[data-testid="username"]').type('admin.sistema');
cy.get('[data-testid="password"]').type('password');
cy.get('button[type="submit"]').click();
```

---

## 10. EVM PARA QA

```
BAC = $15,771
PV (15 días, 2.1%) = $331
EV (test prep, ~10%) = $1,577
AC = $900 (1 QA × part-time)

SPI = $1,577 / $331 = 4.76 (Adelanto)
CPI = $1,577 / $900 = 1.75 (Bajo costo)

Status: 🟡 INICIALIZACIÓN - Ramping up testing
```

---

## 11. HITOS CRÍTICOS

| Hito | Fecha Target | Criterio de Aceptación |
|------|-------------|---|
| Test Infrastructure Ready | 2026-05-20 | CI/CD configurado, JUnit/Mockito working |
| Core Unit Tests | 2026-06-15 | Auth + Student services 85%+ coverage |
| Integration Tests | 2026-06-30 | All flows tested E2E |
| Performance Baseline | 2026-07-15 | Load test results documented |
| UAT Ready | 2026-08-15 | Test cases signed by stakeholders |
| Go-Live | 2026-09-01 | All gates passed, rollback tested |

---

## 12. PROCEDIMIENTOS CRÍTICOS

### Checklist Pre-Go-Live

- ✅ Code coverage ≥ 75%
- ✅ All integration tests passing
- ✅ Performance under 100 users tested
- ✅ Security audit completed
- ✅ Database backup tested
- ✅ Rollback procedure validated
- ✅ Team training completed
- ✅ Monitoring alerts configured
- ✅ Support documentation ready
- ✅ Stakeholder sign-off obtained

---

## 13. RECOMENDACIONES

✅ **Logros iniciales:**
- 4 commits de preparación QA
- Test infrastructure planning completado

🔴 **CRÍTICO - Próximas acciones:**
1. Setup JUnit5 + Mockito framework (Semana 1-2 junio)
2. Create test suite templates
3. Establish code coverage gates in CI/CD
4. Schedule UAT with stakeholders
5. Document test scenarios con equipo dev

⚠️ **Riesgos específicos:**
- Sin tests aceptables hasta Sprint 2 (CRÍTICO)
- Presión de go-live puede comprometer calidad
- Necesita 1.4 FTE dedicado para QA

---

**Documento Generado:** 2026-06-01  
**Estado:** ✅ VERSIÓN 1.0  
**Criticidad:** 🔴 MUY ALTA (bloqueador de Go-Live)

