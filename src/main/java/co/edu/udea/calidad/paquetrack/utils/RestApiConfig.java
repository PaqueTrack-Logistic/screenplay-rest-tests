package co.edu.udea.calidad.paquetrack.utils;

import net.serenitybdd.model.environment.ConfiguredEnvironment;
import net.thucydides.model.util.EnvironmentVariables;

/**
 * Lee la URL base del API desde serenity.conf (clave restapi.baseurl).
 * Evita quemar URLs en el codigo: una unica fuente de configuracion.
 */
public final class RestApiConfig {

    private static final String BASE_URL_KEY = "restapi.baseurl";
    private static final String DEFAULT_BASE_URL = "http://localhost:8088";

    private RestApiConfig() {
    }

    public static String baseUrl() {
        EnvironmentVariables env = ConfiguredEnvironment.getEnvironmentVariables();
        return env.getProperty(BASE_URL_KEY, DEFAULT_BASE_URL);
    }
}
