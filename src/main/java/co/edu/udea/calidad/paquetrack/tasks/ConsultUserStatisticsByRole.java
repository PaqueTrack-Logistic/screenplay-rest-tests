package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task (admin): consulta el consolidado de usuarios agrupados por rol.
 */
public class ConsultUserStatisticsByRole implements Task {

    public static ConsultUserStatisticsByRole now() {
        return instrumented(ConsultUserStatisticsByRole.class);
    }

    @Override
    @Step("{0} consulta las estadisticas de usuarios por rol")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(SendAuthenticatedGet.to(Endpoints.ADMIN_STATS_BY_ROLE));
    }
}
