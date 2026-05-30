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
- **`userinterfaces/`** — Targets (localizadores) por pantalla: `LoginPage`, `ControlPanel`.
- **`interactions/`** — la "menuda" propia: `NavigateTo`, `EnterText`, `ClickOn`.
- **`tasks/`** — acciones de negocio: `LogInToTheControlPanel`.
- **`questions/`** — validaciones: `SignedInUser`.
- **`utils/`** — `UiConfig` (URL del front, no quemada), `UiTestData`.
- **`runners/` + `stepdefinitions/` + `features/`** — Gherkin en ingles, actor generico.

## Escenarios
- `authentication/login.feature` — un administrador inicia sesion por el panel web y llega a su espacio de trabajo.
