# PaqueTrack Screenplay REST — Fábrica Escuela (Calidad de Software 2026-1)

Automatización de pruebas **E2E sobre API REST** del sistema de microservicios **PaqueTrack**, implementando el patrón **Screenplay** con Serenity BDD + Cucumber (Gherkin), atacando el **API Gateway**.

**Estudiante:** Anderson Herrera · **Profesor:** Robinson Coronado García · **Patrón:** Screenplay (Actor · Tasks · Interactions · Questions).

## Reto
Validar la lógica de negocio de PaqueTrack (autenticación/RBAC, envíos, tracking) a través del **gateway** (`http://localhost:8088`), cubriendo **ruta feliz + caminos excepcionales + valores límite**, incluyendo el **flujo event-driven** (RabbitMQ) entre microservicios.

## Stack
| Componente | Versión | Por qué |
|---|---|---|
| Scaffold | `io.github.jumosqu12.screenplayarchitecture` 2.0.1 | Repo del profesor; genera la estructura Screenplay (`type=REST`) |
| Serenity BDD | 4.1.0 | Patrón Screenplay + reportes HTML |
| serenity-screenplay-rest | 4.1.0 | Habilidad `CallAnApi`, interacciones REST (Get/Post) |
| Cucumber | 7.x | Gherkin (en **inglés**) |
| Gradle | 8.14 | Preferencia del profesor |
| Java | 17 LTS | Compatible con Serenity 4.x |
| Lombok / Jackson | — | Modelos `@Builder` / serialización JSON |

## Arquitectura (paquete `co.edu.udea.calidad.paquetrack`)
- **`userinterfaces/Endpoints`** — rutas del API centralizadas (equivalente a los Targets; sin URLs quemadas).
- **`tasks/`** — acciones de negocio (Authenticate, RegisterANewUser, ApproveTheRegisteredUser, CreateAShipment, SearchShipments, RequestShipmentReport, RegisterATrackingEvent, CheckCurrentTrackingStatus, ListPendingUsers, …). Única responsabilidad.
- **`interactions/`** — "la menuda": `SendPost`, `SendGet`, `SendAuthenticatedPost`, `SendAuthenticatedGet` (añaden el Bearer del token recordado).
- **`questions/`** — preguntas **de dominio** (todas las validaciones viven aquí): `GrantedRoles` (roles concedidos), `RejectionReason` (motivo de negocio de un acceso denegado), `AssignedTrackingNumber` (número de guía + su formato), `ReportedState` (estado del envío/cuenta), `NumberOfResults`, `ReportedShipmentTotals` (consolidado del reporte) y `ResponseStatusCode`.
- **`models/`** — `ShipmentRequest` (`@Builder`).
- **`utils/`** — `RestApiConfig` (lee la baseUrl de serenity.conf), `Memory`, `TestData` (datos y reglas de negocio en utilities, no quemados), `UniqueData` (datos únicos por corrida → principio Repetible).
- **`runners/`** + **`stepdefinitions/`** (solo traducen Gherkin↔Java) + **`features/`** organizadas por **capability** (`access/`, `administration/`, `shipments/`, `tracking/`, `delivery/`) para que el reporte Serenity agrupe por dominio de negocio (Gherkin en inglés, **actor genérico** "the operations staff").

## Cómo ejecutar
1. Levantar el stack PaqueTrack (3 Postgres + RabbitMQ + auth:8080 + shipment:8081 + tracking:8082 + gateway:8088).
2. Correr la suite y generar el reporte:
   ```powershell
   $env:JAVA_HOME="C:\Program Files\Java\jdk-17"
   .\gradlew test                 # corre todos los runners
   .\gradlew test "-Dcucumber.filter.tags=@e2e"   # un subconjunto por tags (@access, @shipments, @smoke, @boundary…)
   .\gradlew qualityGate          # pruebas + cobertura + análisis estático (todo junto)
   ```
3. Reporte Serenity: `target/site/serenity/index.html`.
4. Otra URL/entorno (sin tocar código): `serenity.conf` (clave `restapi.baseurl`) o `-Drestapi.baseurl=...`.

## Calidad de código (análisis estático + cobertura)
La automatización se trata como software de producción: se verifica con un *quality gate*.

| Herramienta | Qué verifica | Reporte | Resultado |
|---|---|---|---|
| **JaCoCo** | cobertura de los componentes Screenplay ejercitados por los escenarios | `build/reports/jacoco/test/html/index.html` | **95% instrucciones · 94% líneas · 100% clases** |
| **Checkstyle** | convenciones y mantenibilidad (KISS), sin imports muertos, KISS | `build/reports/checkstyle/main.html` | **0 violaciones** |
| **SpotBugs** | patrones de bug (confianza alta, esfuerzo máximo) | `build/reports/spotbugs/main.html` | **0 hallazgos** |

```powershell
.\gradlew qualityGate     # test + jacocoTestReport + checkstyle + spotbugs
```

## Escenarios cubiertos (23, todos en verde) — enfoque en reglas de negocio
Las features están escritas como **criterios de aceptación del negocio** (qué garantiza la plataforma), no como aserciones técnicas de "status code".

- **access.feature (7):** un administrador obtiene privilegios de administración · un solicitante queda **pendiente de aprobación** · *regla clave:* **un solicitante no puede operar hasta que un administrador apruebe su cuenta** (`AUTH_PENDING_APPROVAL`) · acceso negado por credenciales inválidas (`AUTH_INVALID_CREDENTIALS`) · **valor límite** sobre la longitud mínima de contraseña (7 → rechazada, 8 → aceptada).
- **user_management.feature (4) — RBAC:** un administrador revisa solicitantes pendientes · un solicitante **aprobado como operador** obtiene su rol · revisar pendientes **exige sesión** (anónimo → rechazado) · un **operador no puede** administrar solicitantes (control de acceso por rol).
- **shipments.feature (9):** registrar un envío con **número de guía único** (formato `PQ-AAAAMMDD-XXXXXX`) que nace en estado `CREATED` · localizar por guía / por id · buscar por remitente / por destinatario · *regla del negocio:* la búsqueda exige **exactamente un criterio** (ambos / ninguno → consulta inválida) · reporte gerencial consolidado por estado · reporte con rango de fechas invertido → rechazado.
- **tracking.feature (2):** la plataforma **publica el catálogo** de eventos que entiende · un evento **no soportado** es rechazado.
- **delivery_lifecycle.feature (1) — E2E:** un envío recorre **todo su ciclo de vida** a medida que el courier reporta hitos: `CREATED → IN_TRANSIT → OUT_FOR_DELIVERY → DELIVERED` (propagación **asíncrona** vía RabbitMQ, validada con espera hasta el estado esperado).

## Técnicas de prueba (caja negra) aplicadas
- **Análisis de valor límite (BVA):** longitud de contraseña en el registro (just-below 7 vs. límite 8).
- **Partición de equivalencia / clases inválidas:** búsqueda de envíos (un criterio = válido; cero o dos = inválido), tipo de evento soportado vs. no soportado, rango de fechas válido vs. invertido.
- **Reglas de autorización (RBAC):** mismas operaciones evaluadas desde admin / operador / anónimo.
- **Flujo de estados (state machine):** la máquina de estados completa de la entrega como recorrido E2E.

## Buenas prácticas aplicadas (observaciones del profe)
- ✅ Gherkin **en inglés**, en **términos de negocio** (criterios de aceptación), **actor genérico** ("the operations staff", rol y no nombre propio).
- ✅ Validaciones **solo en Questions de dominio** (una responsabilidad por pregunta); Tasks de negocio; **Interactions** para la "menuda" REST.
- ✅ **Sin hardcode**: URLs en `serenity.conf`, datos y reglas en `utils/TestData`, rutas en `Endpoints`.
- ✅ **FIRST**: datos **únicos** por corrida (Repetible) y escenarios **independientes** (cada uno prepara su propio contexto; sin orden forzado).
- ✅ Step Definitions = **solo traducción** Gherkin↔Java; reporte **Serenity** generado.
- ✅ Generado con el **scaffold** del profesor (`type=REST`).
