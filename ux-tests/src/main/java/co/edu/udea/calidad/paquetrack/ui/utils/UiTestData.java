package co.edu.udea.calidad.paquetrack.ui.utils;

/**
 * Datos de prueba de la capa UX (no quemados en steps/tasks). El actor es un
 * rol generico, no un nombre propio.
 */
public final class UiTestData {

    private UiTestData() {
    }

    public static final String ACTOR = "the operations staff";

    // Credenciales semilla del entorno (mismas del backend)
    public static final String ADMIN_EMAIL = "admin@logistics.com";
    public static final String ADMIN_PASSWORD = "password";
    // Password con formato valido pero incorrecto (para login fallido)
    public static final String WRONG_PASSWORD = "Wrong2026*";
    // Password valido para registrar nuevos solicitantes
    public static final String NEW_USER_PASSWORD = "Pruebas2026*";

    /** Correo unico por corrida (principio FIRST: repetible). */
    public static String uniqueEmail() {
        return "qa.ui." + System.currentTimeMillis() + "@logistics.com";
    }
}
