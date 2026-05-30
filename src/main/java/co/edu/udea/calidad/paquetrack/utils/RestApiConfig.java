package co.edu.udea.calidad.paquetrack.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Resuelve la URL base del API sin quemarla en el codigo. Orden de prioridad:
 *   1. Propiedad del sistema -Drestapi.baseurl=...  (override puntual por consola)
 *   2. Archivo .env en la raiz (clave RESTAPI_BASEURL) -> config local del dev
 *   3. Archivo restapi.properties (clave restapi.baseurl) -> fallback versionado
 *   4. Valor por defecto (gateway local)
 * Una unica fuente de verdad, invocada desde el Hook que arma al Actor.
 */
public final class RestApiConfig {

    private static final String SYSTEM_PROPERTY = "restapi.baseurl";
    private static final String ENV_FILE = ".env";
    private static final String ENV_KEY = "RESTAPI_BASEURL";
    private static final String PROPERTIES_RESOURCE = "/restapi.properties";
    private static final String PROPERTIES_KEY = "restapi.baseurl";
    private static final String DEFAULT_BASE_URL = "http://localhost:8088";

    private RestApiConfig() {
    }

    public static String baseUrl() {
        String fromSystem = System.getProperty(SYSTEM_PROPERTY);
        if (isPresent(fromSystem)) {
            return fromSystem.trim();
        }
        String fromEnv = readKey(envFileProperties(), ENV_KEY);
        if (isPresent(fromEnv)) {
            return fromEnv.trim();
        }
        String fromProperties = readKey(classpathProperties(), PROPERTIES_KEY);
        if (isPresent(fromProperties)) {
            return fromProperties.trim();
        }
        return DEFAULT_BASE_URL;
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

    private static Properties classpathProperties() {
        try (InputStream in = RestApiConfig.class.getResourceAsStream(PROPERTIES_RESOURCE)) {
            if (in == null) {
                return null;
            }
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            return null;
        }
    }
}
