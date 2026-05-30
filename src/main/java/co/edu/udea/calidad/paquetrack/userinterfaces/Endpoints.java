package co.edu.udea.calidad.paquetrack.userinterfaces;

/**
 * Rutas del API expuestas por el API Gateway de PaqueTrack.
 * Centraliza los paths (equivalente a los Targets en Screenplay UX):
 * ninguna ruta se quema dentro de Tasks/Interactions.
 *
 * Reescrituras del gateway:
 *   /api/auth/**     -> auth-service /api/v1/auth/**  y /api/v1/admin/**
 *   /api/shipments/**-> shipment-service (directo)
 *   /api/tracking/** -> tracking-service /api/v1/tracking/**
 */
public final class Endpoints {

    private Endpoints() {
    }

    // --- Autenticacion ---
    public static final String LOGIN = "/api/auth/login";
    public static final String REGISTER = "/api/auth/register";
    public static final String REFRESH = "/api/auth/refresh";

    // --- Administracion de usuarios (RBAC: ROLE_ADMIN) ---
    public static final String ADMIN_PENDING_USERS = "/api/auth/admin/users/pending";
    public static final String ADMIN_ASSIGNABLE_ROLES = "/api/auth/admin/users/assignable-roles";
    public static final String ADMIN_STATS_BY_ROLE = "/api/auth/admin/stats/users-by-role";

    public static String approveUser(String userId) {
        return "/api/auth/admin/users/" + userId + "/approve";
    }

    public static String rejectUser(String userId) {
        return "/api/auth/admin/users/" + userId + "/reject";
    }

    // --- Envios (shipment-service) ---
    public static final String SHIPMENTS = "/api/shipments";
    public static final String SHIPMENTS_SEARCH = "/api/shipments/search";
    public static final String SHIPMENTS_REPORT = "/api/shipments/report";

    public static String shipmentById(String id) {
        return SHIPMENTS + "/" + id;
    }

    public static String shipmentByTracking(String trackingId) {
        return SHIPMENTS + "/tracking/" + trackingId;
    }

    public static String shipmentHistory(String id) {
        return SHIPMENTS + "/" + id + "/history";
    }

    // --- Tracking (tracking-service) ---
    public static final String EVENT_TYPES = "/api/tracking/eventTypes";

    public static String trackingEvents(String shipmentId) {
        return "/api/tracking/" + shipmentId + "/events";
    }

    public static String trackingCurrent(String shipmentId) {
        return "/api/tracking/" + shipmentId + "/current";
    }

    public static String trackingHistory(String shipmentId) {
        return "/api/tracking/" + shipmentId + "/history";
    }
}
