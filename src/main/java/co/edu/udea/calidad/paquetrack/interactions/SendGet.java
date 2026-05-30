package co.edu.udea.calidad.paquetrack.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction: GET SIN autenticacion (endpoints publicos, p. ej. catalogo de eventos).
 */
public class SendGet implements Interaction {

    private final String path;

    public SendGet(String path) {
        this.path = path;
    }

    public static SendGet to(String path) {
        return instrumented(SendGet.class, path);
    }

    @Override
    @Step("{0} envia GET a #path")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Get.resource(path));
    }
}
