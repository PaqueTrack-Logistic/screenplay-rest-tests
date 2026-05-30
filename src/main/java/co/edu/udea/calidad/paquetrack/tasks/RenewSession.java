package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendPost;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task de negocio: renueva la sesion del actor usando el refresh token recordado,
 * sin volver a enviar credenciales. Si tiene exito, actualiza los tokens.
 */
public class RenewSession implements Task {

    private static final int HTTP_OK = 200;

    public static RenewSession now() {
        return instrumented(RenewSession.class);
    }

    @Override
    @Step("{0} renueva su sesion con el refresh token")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SendPost.to(Endpoints.REFRESH)
                        .withBody(Map.of("refreshToken", actor.recall(Memory.REFRESH_TOKEN)))
        );
        Response response = LastResponse.received().answeredBy(actor);
        if (response.statusCode() == HTTP_OK) {
            actor.remember(Memory.ACCESS_TOKEN, response.jsonPath().getString("accessToken"));
            actor.remember(Memory.REFRESH_TOKEN, response.jsonPath().getString("refreshToken"));
        }
    }
}
