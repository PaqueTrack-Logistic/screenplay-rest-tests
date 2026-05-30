package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendPost;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import co.edu.udea.calidad.paquetrack.utils.TokenCache;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task de negocio: el actor inicia sesion en el gateway y, si tiene exito,
 * recuerda su token de acceso para las llamadas protegidas siguientes.
 */
public class Authenticate implements Task {

    private static final int HTTP_OK = 200;
    private static final int HTTP_TOO_MANY_REQUESTS = 429;
    // El auth-service limita los intentos de login; ante un 429 se espera a que la
    // ventana se recupere y se reintenta, autoregulando la cadencia de la suite.
    private static final int MAX_ATTEMPTS = 8;
    private static final long BACKOFF_MS = 3000L;

    private final String email;
    private final String password;

    public Authenticate(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Authenticate withCredentials(String email, String password) {
        return instrumented(Authenticate.class, email, password);
    }

    @Override
    @Step("{0} inicia sesion con el correo #email")
    public <T extends Actor> void performAs(T actor) {
        Response response = login(actor);
        if (response.statusCode() == HTTP_OK) {
            String accessToken = response.jsonPath().getString("accessToken");
            actor.remember(Memory.ACCESS_TOKEN, accessToken);
            actor.remember(Memory.REFRESH_TOKEN, response.jsonPath().getString("refreshToken"));
            TokenCache.put(email, accessToken);
        } else {
            // Login no exitoso: no conservar un token anterior (evita que el actor
            // herede privilegios de una sesion previa y enmascare el resultado).
            actor.forget(Memory.ACCESS_TOKEN);
            actor.forget(Memory.REFRESH_TOKEN);
        }
    }

    private <T extends Actor> Response login(T actor) {
        Response response = null;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            actor.attemptsTo(
                    SendPost.to(Endpoints.LOGIN).withBody(Map.of("email", email, "password", password))
            );
            response = LastResponse.received().answeredBy(actor);
            if (response.statusCode() != HTTP_TOO_MANY_REQUESTS) {
                return response;
            }
            pause();
        }
        return response;
    }

    private void pause() {
        try {
            Thread.sleep(BACKOFF_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
