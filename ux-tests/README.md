# PaqueTrack Screenplay UX (pruebas de navegador)

Pruebas **end-to-end de interfaz** del frontend de PaqueTrack con el patrón **Screenplay** (Serenity BDD + Cucumber + **Selenium**). Mismo patrón que el módulo REST, pero el actor usa la habilidad **`BrowseTheWeb`** (abre Chrome y opera la pantalla como un usuario real).

> Es un módulo **independiente** del suite REST (raíz del repo). El entregable principal sigue siendo REST (requisito del profe); esta capa UX es cobertura adicional de la cara visual.

## Requisitos para correr
1. **Backend** PaqueTrack arriba (gateway en `:8088`) + microservicios.
2. **Frontend** `ui-service` corriendo en `:5173` y apuntando al gateway. Para evitar CORS en local, el front se ejecuta con un **proxy de Vite** que reenvía `/api` al gateway y elimina el header `Origin`:
   ```js
   // vite.config.js del front
   server: {
     proxy: {
       '/api': {
         target: 'http://localhost:8088',
         changeOrigin: true,
         configure: (proxy) => {
           proxy.on('proxyReq', (r) => { r.removeHeader('origin'); r.removeHeader('referer'); });
         },
       },
     },
   }
   ```
   y `.env` del front con `VITE_API_GATEWAY_URL=http://localhost:5173` (mismo-origen → proxy).
   Levantar: `npm install && npm run dev`.
3. **Chrome** instalado (el driver lo resuelve Selenium Manager automaticamente).

## Ejecutar
```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
# desde la raiz del repo, apuntando a este modulo:
.\gradlew -p ux-tests test
.\gradlew -p ux-tests test "-Dheadless.mode=true"   # sin ventana (CI)
.\gradlew -p ux-tests test "-Dui.baseurl=http://otro-host:5173"
```
Reporte Serenity (con **capturas de pantalla** de cada paso): `ux-tests/target/site/serenity/index.html`.

## Arquitectura (paquete `co.edu.udea.calidad.paquetrack.ui`)
- **`userinterfaces/`** — Targets (localizadores) por pantalla: `LoginPage`, `ControlPanel`, `RegistrationPage`, `ShipmentsPage`, `TrackingPage`, `AdminUsersPage`.
- **`interactions/`** — la "menuda" propia: `NavigateTo`, `EnterText`, `ClickOn`, `ChooseOption`, `SetFieldValue`.
- **`tasks/`** — acciones de negocio: `SignIn`, `LogInToTheControlPanel`, `LogOut`, `RequestAccessThroughTheUI`, `RegisterAShipmentThroughTheUI`, `RegisterATrackingEventThroughTheUI`, `ApproveApplicantThroughTheUI`.
- **`questions/`** — validaciones: `SignedInUser`, `AccessError`, `CurrentPageUrl`, `RegistrationOutcome`, `AssignedTrackingNumber`, `CreatedShipmentInternalId`, `TrackingEventOutcome`, `AdminActionOutcome`.
- **`utils/`** — `UiConfig` (URLs del front, no quemadas), `UiTestData` (datos, unicos por corrida).
- **`runners/` + `stepdefinitions/` + `features/`** — Gherkin en ingles, actor generico, por capability.

## Escenarios (7, en verde) — viajes de usuario en el navegador
- `authentication/login.feature` (3): inicio de sesion del administrador · acceso rechazado con contrasena incorrecta · cierre de sesion y regreso al login.
- `registration/registration.feature` (1): un nuevo solicitante pide acceso desde el formulario y la plataforma confirma que queda pendiente de aprobacion.
- `shipments/shipments.feature` (1): un operador registra un envio desde el formulario web y obtiene su numero de guia (PQ-...).
- `administration/administration.feature` (1) — RBAC visual: un administrador revisa y **aprueba** a un solicitante recien registrado desde el panel.
- `tracking/tracking.feature` (1) — E2E: un operador crea un envio y **reporta un evento de tracking** para el desde la web.
