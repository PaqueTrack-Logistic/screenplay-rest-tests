package co.edu.udea.calidad.paquetrack.ui.utils;

import java.util.UUID;

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

    /** Id interno (UUID) de un envio que no existe, para el camino "no encontrado". */
    public static String unknownShipmentId() {
        return UUID.randomUUID().toString();
    }

    // Datos por defecto del envio (no quemados en steps/tasks)
    public static final String SHIPMENT_ADDRESS = "Calle 10 # 20-30";
    public static final String SENDER_CITY = "Medellin";
    public static final String RECIPIENT_CITY = "Bogota";
    public static final String SHIPMENT_WEIGHT = "2.5";

    // Rol a asignar al aprobar un solicitante desde la UI
    public static final String OPERATOR_ROLE = "ROLE_OPERATOR";

    // Ubicacion por defecto al reportar un evento de tracking
    public static final String EVENT_LOCATION = "Centro logistico Medellin";
}
