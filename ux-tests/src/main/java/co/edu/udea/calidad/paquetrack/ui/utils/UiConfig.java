package co.edu.udea.calidad.paquetrack.ui.utils;

import net.serenitybdd.model.environment.ConfiguredEnvironment;

/**
 * Lee la URL base del frontend desde serenity.conf (clave ui.baseurl), evitando
 * quemar URLs en el codigo. Sobreescribible con -Dui.baseurl=...
 */
public final class UiConfig {

    private static final String BASE_URL_KEY = "ui.baseurl";
    private static final String DEFAULT_BASE_URL = "http://localhost:5173";

    private UiConfig() {
    }

    public static String baseUrl() {
        return ConfiguredEnvironment.getEnvironmentVariables().getProperty(BASE_URL_KEY, DEFAULT_BASE_URL);
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
}
