package co.edu.udea.calidad.paquetrack.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache de tokens de acceso por credencial (vida = ejecucion de la suite).
 *
 * El auth-service limita la cantidad de inicios de sesion (rate limiting). Las
 * precondiciones ("un administrador ha iniciado sesion") solo necesitan un token
 * valido, no ejercitar el login; reutilizar el token evita repetir decenas de
 * autenticaciones y mantener la cadencia por debajo del limite. NO afecta la
 * independencia de los escenarios: el token es una credencial compartida de solo
 * lectura, no estado de prueba (cada escenario sigue creando sus propios datos).
 */
public final class TokenCache {

    private static final Map<String, String> TOKENS = new ConcurrentHashMap<>();

    private TokenCache() {
    }

    public static String get(String email) {
        return TOKENS.get(email);
    }

    public static void put(String email, String accessToken) {
        if (accessToken != null) {
            TOKENS.put(email, accessToken);
        }
    }
}
