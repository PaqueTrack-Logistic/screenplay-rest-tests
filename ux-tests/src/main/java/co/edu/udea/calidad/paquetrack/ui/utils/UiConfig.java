package co.edu.udea.calidad.paquetrack.ui.utils;

import net.serenitybdd.model.environment.ConfiguredEnvironment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Resuelve la configuracion de las pruebas de UI sin quemar valores en el codigo.
 * Pensado para que el front pueda vivir en un ambiente DISTINTO al del backend
 * (otra URL/host): basta apuntar UI_BASEURL al front del ambiente que toque.
 *
 * Orden de prioridad (de mayor a menor):
 *   1. Propiedad del sistema -Dui.baseurl=... / -Dui.delay.ms=...  (override puntual por consola)
 *   2. Archivo .env del modulo ux-tests (UI_BASEURL, UI_DELAY_MS)  -> config por ambiente/dev
 *   3. serenity.conf (ui.baseurl, ui.delay.ms)                     -> fallback versionado
 *   4. Valor por defecto
 * Una unica fuente de verdad para toda la suite de UI.
 */
public final class UiConfig {

    private static final String ENV_FILE = ".env";

    private static final String BASE_URL_SYS = "ui.baseurl";
    private static final String BASE_URL_ENV = "UI_BASEURL";
    private static final String BASE_URL_CONF = "ui.baseurl";
    private static final String DEFAULT_BASE_URL = "http://localhost:5173";

    private static final String DELAY_SYS = "ui.delay.ms";
    private static final String DELAY_ENV = "UI_DELAY_MS";
    private static final String DELAY_CONF = "ui.delay.ms";
    private static final long DEFAULT_DELAY_MS = 0L;

    private UiConfig() {
    }

    public static String baseUrl() {
        return resolve(BASE_URL_SYS, BASE_URL_ENV, BASE_URL_CONF, DEFAULT_BASE_URL);
    }

    /**
     * Delay "solo para observar" la prueba en vivo (NO productivo). Por defecto 0 = sin
     * pausa: la suite real corre con esperas explicitas (WaitUntil), nunca sleeps. Se
     * activa por AMBIENTE sin tocar el codigo: UI_DELAY_MS=2000 en .env (o -Dui.delay.ms=2000).
     */
    public static long observeDelayMillis() {
        String raw = resolve(DELAY_SYS, DELAY_ENV, DELAY_CONF, String.valueOf(DEFAULT_DELAY_MS));
        try {
            long value = Long.parseLong(raw.trim());
            return value < 0 ? 0L : value;
        } catch (NumberFormatException e) {
            return DEFAULT_DELAY_MS;
        }
    }

    public static String loginUrl() {
        return baseUrl() + "/login";
    }

    public static String registerUrl() {
        return baseUrl() + "/register";
    }

    public static String shipmentsUrl() {
        return baseUrl() + "/shipments";
    }

    public static String trackingUrl() {
        return baseUrl() + "/tracking";
    }

    public static String adminUsersUrl() {
        return baseUrl() + "/admin/users";
    }

    private static String resolve(String systemProperty, String envKey, String confKey, String defaultValue) {
        String fromSystem = System.getProperty(systemProperty);
        if (isPresent(fromSystem)) {
            return fromSystem.trim();
        }
        String fromEnv = readKey(envFileProperties(), envKey);
        if (isPresent(fromEnv)) {
            return fromEnv.trim();
        }
        String fromConf = ConfiguredEnvironment.getEnvironmentVariables().getProperty(confKey);
        if (isPresent(fromConf)) {
            return fromConf.trim();
        }
        return defaultValue;
    }

    private static boolean isPresent(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static String readKey(Properties properties, String key) {
        return properties == null ? null : properties.getProperty(key);
    }

    private static Properties envFileProperties() {
        File envFile = new File(ENV_FILE);
        if (!envFile.exists()) {
            return null;
        }
        try (InputStream in = new FileInputStream(envFile)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            return null;
        }
    }
}
