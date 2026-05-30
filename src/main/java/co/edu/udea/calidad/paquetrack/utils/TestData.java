package co.edu.udea.calidad.paquetrack.utils;

import co.edu.udea.calidad.paquetrack.models.ShipmentRequest;

import java.math.BigDecimal;

/**
 * Datos de prueba centralizados (NO quemados en steps/tasks).
 * Credenciales semilla del entorno y factories de modelos.
 */
public final class TestData {

    private TestData() {
    }

    // Actor generico de negocio (rol, no nombre propio) usado en todos los escenarios
    public static final String ACTOR = "the operations staff";

    // Credenciales semilla del auth-service (data.sql)
    public static final String ADMIN_EMAIL = "admin@logistics.com";
    public static final String ADMIN_PASSWORD = "password";
    public static final String DEFAULT_PASSWORD = "Pruebas2026*";
    // Password con formato valido pero que NO corresponde a la cuenta (credenciales invalidas)
    public static final String WRONG_PASSWORD = "Wrong2026*";

    // Roles asignables
    public static final String ROLE_OPERATOR = "ROLE_OPERATOR";
    public static final String ROLE_LOGISTICS = "ROLE_LOGISTICS";

    // Regla de negocio del registro: longitud minima de contrasena
    public static final int MIN_PASSWORD_LENGTH = 8;

    // Estado funcional de una cuenta recien registrada (a la espera de aprobacion)
    public static final String ACCOUNT_PENDING = "PENDING";

    // Codigos de negocio que explican por que la plataforma niega el acceso
    public static final String REASON_PENDING_APPROVAL = "AUTH_PENDING_APPROVAL";
    public static final String REASON_INVALID_CREDENTIALS = "AUTH_INVALID_CREDENTIALS";

    // Rango de fechas del reporte gerencial (anio en curso)
    public static final String REPORT_FROM = "2026-01-01";
    public static final String REPORT_TO = "2026-12-31";

    /** Genera una contrasena de la longitud exacta pedida (para valor limite). */
    public static String passwordOfLength(int length) {
        StringBuilder sb = new StringBuilder("Ab1*");
        while (sb.length() < length) {
            sb.append('x');
        }
        return sb.substring(0, length);
    }

    public static ShipmentRequest defaultShipment() {
        return ShipmentRequest.builder()
                .senderName("Ana Perez")
                .senderAddress("Cra 10 #20-30")
                .senderCity("Medellin")
                .recipientName("Luis Gomez")
                .recipientAddress("Calle 5 #6-7")
                .recipientCity("Bogota")
                .weightKg(new BigDecimal("2.5"))
                .build();
    }

    // Destinatario por defecto cuando el escenario solo fija el remitente
    public static final String DEFAULT_RECIPIENT = "Cliente Final";

    public static ShipmentRequest shipmentFrom(String sender) {
        return shipmentFrom(sender, DEFAULT_RECIPIENT);
    }

    public static ShipmentRequest shipmentFrom(String sender, String recipient) {
        return shipmentFrom(sender, "Medellin", recipient, "Bogota");
    }

    public static ShipmentRequest shipmentFrom(String sender, String senderCity,
                                               String recipient, String recipientCity) {
        return ShipmentRequest.builder()
                .senderName(sender)
                .senderAddress("Direccion origen")
                .senderCity(senderCity)
                .recipientName(recipient)
                .recipientAddress("Direccion destino")
                .recipientCity(recipientCity)
                .weightKg(new BigDecimal("1.0"))
                .build();
    }
}
