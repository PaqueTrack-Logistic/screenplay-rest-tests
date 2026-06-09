package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.ClickOn;
import co.edu.udea.calidad.paquetrack.ui.interactions.EnterText;
import co.edu.udea.calidad.paquetrack.ui.interactions.Pause;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.LoginPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: diligencia el formulario de login y lo envia (sin esperar resultado).
 * Sirve tanto para el camino feliz como para credenciales invalidas.
 */
public class SignIn implements Task {

    private final String email;
    private final String password;

    public SignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static SignIn withCredentials(String email, String password) {
        return instrumented(SignIn.class, email, password);
    }

    @Override
    @Step("{0} diligencia el formulario de login con #email")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterText.of(email, LoginPage.EMAIL_FIELD),
                EnterText.of(password, LoginPage.PASSWORD_FIELD),
                Pause.toObserve(),                   // demo: ver el formulario diligenciado (configurable por UI_DELAY_MS)
                ClickOn.the(LoginPage.SIGN_IN_BUTTON)
        );
    }
}
