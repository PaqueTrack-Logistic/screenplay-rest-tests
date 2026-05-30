package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendPost;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task de negocio: registra un nuevo usuario. Recuerda email/password/userId
 * para reutilizarlos (aprobacion, login posterior) en flujos E2E.
 */
public class RegisterANewUser implements Task {

    private final String email;
    private final String password;

    public RegisterANewUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static RegisterANewUser withCredentials(String email, String password) {
        return instrumented(RegisterANewUser.class, email, password);
    }

    @Override
    @Step("{0} registra la cuenta #email")
    public <T extends Actor> void performAs(T actor) {
        actor.remember(Memory.USER_EMAIL, email);
        actor.remember(Memory.USER_PASSWORD, password);
        actor.attemptsTo(
                SendPost.to(Endpoints.REGISTER).withBody(Map.of("email", email, "password", password))
        );
        String userId = LastResponse.received().answeredBy(actor).jsonPath().getString("userId");
        if (userId != null) {
            actor.remember(Memory.USER_ID, userId);
        }
    }
}
