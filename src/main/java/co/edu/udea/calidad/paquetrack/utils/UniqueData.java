package co.edu.udea.calidad.paquetrack.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Genera datos unicos por ejecucion para cumplir el principio FIRST (Repetible):
 * cada corrida usa correos distintos, evitando colisiones con datos previos.
 */
public final class UniqueData {

    private static final AtomicLong COUNTER = new AtomicLong(0);

    private UniqueData() {
    }

    public static String email() {
        return "qa.user." + uniqueSuffix() + "@logistics.com";
    }

    public static String uniqueSuffix() {
        return System.currentTimeMillis() + "" + COUNTER.incrementAndGet();
    }
}
