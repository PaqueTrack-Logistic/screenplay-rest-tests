package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task (admin): consulta los roles que la plataforma permite asignar al aprobar
 * un solicitante.
 */
public class ConsultAssignableRoles implements Task {

    public static ConsultAssignableRoles available() {
        return instrumented(ConsultAssignableRoles.class);
    }

    @Override
    @Step("{0} consulta los roles asignables")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(SendAuthenticatedGet.to(Endpoints.ADMIN_ASSIGNABLE_ROLES));
    }
}
