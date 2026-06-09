package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.ClickOn;
import co.edu.udea.calidad.paquetrack.ui.interactions.EnterText;
import co.edu.udea.calidad.paquetrack.ui.interactions.Pause;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.RegistrationPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task de negocio (UI): diligencia el formulario de registro con correo,
 * contrasena y confirmacion EXPLICITAS y lo envia, sin asumir el resultado.
 * A diferencia de {@link RequestAccessThroughTheUI} (camino feliz, espera el
 * exito), aqui la confirmacion puede no coincidir o el correo ya existir: sirve
 * para los caminos de rechazo, donde la Question lee el mensaje de error.
 */
public class SubmitAccessRequest implements Task {

    private final String email;
    private final String password;
    private final String confirmation;

    public SubmitAccessRequest(String email, String password, String confirmation) {
        this.email = email;
        this.password = password;
        this.confirmation = confirmation;
    }

    public static SubmitAccessRequest with(String email, String password, String confirmation) {
        return instrumented(SubmitAccessRequest.class, email, password, confirmation);
    }

    @Override
    @Step("{0} envia el formulario de registro con el correo #email")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterText.of(email, RegistrationPage.EMAIL_FIELD),
                EnterText.of(password, RegistrationPage.PASSWORD_FIELD),
                EnterText.of(confirmation, RegistrationPage.CONFIRM_FIELD),
                Pause.toObserve(),                   // demo: ver el formulario diligenciado (configurable por UI_DELAY_MS)
                ClickOn.the(RegistrationPage.SUBMIT_BUTTON)
        );
    }
}
