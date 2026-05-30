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
 * Task (admin): rechaza la solicitud de registro del usuario recordado.
 * Contraparte de {@link ApproveTheRegisteredUser}: el solicitante rechazado
 * no podra iniciar sesion.
 */
public class RejectTheRegisteredUser implements Task {

    public static RejectTheRegisteredUser now() {
        return instrumented(RejectTheRegisteredUser.class);
    }

    @Override
    @Step("{0} rechaza al usuario registrado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SendAuthenticatedPost.to(Endpoints.rejectUser(actor.recall(Memory.USER_ID)))
                        .withBody(Map.of())
        );
    }
}
