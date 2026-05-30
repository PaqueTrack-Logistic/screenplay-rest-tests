package co.edu.udea.calidad.paquetrack.utils;

/**
 * Claves de memoria del Actor (actor.remember/recall).
 * Permite encadenar pasos (token, ids) sin variables estaticas ni estado global.
 */
public final class Memory {

    private Memory() {
    }

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String USER_ID = "userId";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userPassword";
    public static final String SHIPMENT_ID = "shipmentId";
    public static final String TRACKING_ID = "trackingId";
}
