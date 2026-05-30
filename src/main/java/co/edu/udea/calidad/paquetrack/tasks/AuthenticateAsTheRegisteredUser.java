package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.utils.Memory;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: inicia sesion con las credenciales del usuario recien registrado
 * (recordadas), para validar RBAC desde la perspectiva de ese usuario.
 */
public class AuthenticateAsTheRegisteredUser implements Task {

    public static AuthenticateAsTheRegisteredUser now() {
        return instrumented(AuthenticateAsTheRegisteredUser.class);
    }

    @Override
    @Step("{0} inicia sesion con la cuenta registrada")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Authenticate.withCredentials(actor.recall(Memory.USER_EMAIL), actor.recall(Memory.USER_PASSWORD))
        );
    }
}
