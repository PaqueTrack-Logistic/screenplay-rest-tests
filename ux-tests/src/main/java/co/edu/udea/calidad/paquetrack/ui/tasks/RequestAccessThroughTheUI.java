package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.ClickOn;
import co.edu.udea.calidad.paquetrack.ui.interactions.EnterText;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.RegistrationPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task de negocio (UI): un solicitante pide acceso diligenciando el formulario
 * de registro (correo + contrasena + confirmacion). Espera la confirmacion.
 */
public class RequestAccessThroughTheUI implements Task {

    private static final int FEEDBACK_TIMEOUT_SECONDS = 20;

    private final String email;
    private final String password;

    public RequestAccessThroughTheUI(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static RequestAccessThroughTheUI withCredentials(String email, String password) {
        return instrumented(RequestAccessThroughTheUI.class, email, password);
    }

    @Override
    @Step("{0} solicita acceso con el correo #email")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterText.of(email, RegistrationPage.EMAIL_FIELD),
                EnterText.of(password, RegistrationPage.PASSWORD_FIELD),
                EnterText.of(password, RegistrationPage.CONFIRM_FIELD),
                ClickOn.the(RegistrationPage.SUBMIT_BUTTON),
                WaitUntil.the(RegistrationPage.SUCCESS_ALERT, isVisible())
                        .forNoMoreThan(FEEDBACK_TIMEOUT_SECONDS).seconds()
        );
    }
}
