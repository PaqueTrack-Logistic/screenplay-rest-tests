package co.edu.udea.calidad.paquetrack.ui.interactions;

import co.edu.udea.calidad.paquetrack.ui.utils.UiConfig;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction (la "menuda"): pausa la ejecucion un instante.
 *
 * ATENCION: el delay es SOLO para SOCIALIZAR/OBSERVAR la prueba en vivo (ver el
 * navegador paso a paso). NO es productivo: en la suite real se usan esperas
 * explicitas (WaitUntil), nunca sleeps.
 *
 * Por eso el valor NO esta quemado: las tasks usan {@link #toObserve()}, que lee
 * el delay desde la configuracion por ambiente (UI_DELAY_MS en .env / -Dui.delay.ms).
 * Por defecto es 0 => no hace nada (la suite corre sin pausas). Para la demo se
 * activa con UI_DELAY_MS=2000 sin tocar el codigo.
 */
public class Pause implements Interaction {

    private final long millis;

    public Pause(long millis) {
        this.millis = millis;
    }

    /**
     * Pausa configurable por ambiente (UI_DELAY_MS). 0 = sin pausa. Es la que usan
     * las tasks: mantiene el delay fuera del codigo y apagado por defecto.
     */
    public static Pause toObserve() {
        return instrumented(Pause.class, UiConfig.observeDelayMillis());
    }

    public static Pause forSeconds(long seconds) {
        return instrumented(Pause.class, seconds * 1000L);
    }

    public static Pause forMillis(long millis) {
        return instrumented(Pause.class, millis);
    }

    @Override
    @Step("{0} hace una pausa de #millis ms (solo para observar)")
    public <T extends Actor> void performAs(T actor) {
        if (millis <= 0) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
