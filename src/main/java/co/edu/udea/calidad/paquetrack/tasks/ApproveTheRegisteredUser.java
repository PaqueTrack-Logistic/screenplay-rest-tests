package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedPost;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task (admin): aprueba al usuario registrado (id recordado) asignandole un rol.
 */
public class ApproveTheRegisteredUser implements Task {

    private final String role;

    public ApproveTheRegisteredUser(String role) {
        this.role = role;
    }

    public static ApproveTheRegisteredUser asRole(String role) {
        return instrumented(ApproveTheRegisteredUser.class, role);
    }

    @Override
    @Step("{0} aprueba al usuario registrado con rol #role")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SendAuthenticatedPost.to(Endpoints.approveUser(actor.recall(Memory.USER_ID)))
                        .withBody(Map.of("role", role))
        );
    }
}
