package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task (admin): solicita la lista de usuarios pendientes de aprobacion.
 * Sirve tambien para validar RBAC (operador -> 403, sin token -> 401).
 */
public class ListPendingUsers implements Task {

    public static ListPendingUsers request() {
        return instrumented(ListPendingUsers.class);
    }

    @Override
    @Step("{0} solicita la lista de usuarios pendientes")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(SendAuthenticatedGet.to(Endpoints.ADMIN_PENDING_USERS));
    }
}
