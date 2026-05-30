package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: consulta el catalogo publico de tipos de evento de tracking.
 */
public class ConsultEventTypes implements Task {

    public static ConsultEventTypes catalog() {
        return instrumented(ConsultEventTypes.class);
    }

    @Override
    @Step("{0} consulta el catalogo de tipos de evento")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(SendGet.to(Endpoints.EVENT_TYPES));
    }
}
