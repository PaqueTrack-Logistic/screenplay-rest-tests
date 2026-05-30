package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.AdminUsersPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task de negocio (UI): el administrador aprueba (con el rol por defecto del
 * formulario) la solicitud del correo dado, ubicando su fila en la tabla.
 */
public class ApproveApplicantThroughTheUI implements Task {

    private static final int TIMEOUT_SECONDS = 20;

    private final String applicantEmail;

    public ApproveApplicantThroughTheUI(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public static ApproveApplicantThroughTheUI forApplicant(String applicantEmail) {
        return instrumented(ApproveApplicantThroughTheUI.class, applicantEmail);
    }

    @Override
    @Step("{0} aprueba la solicitud de #applicantEmail")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AdminUsersPage.APPROVE_BUTTON_FOR.of(applicantEmail), isVisible())
                        .forNoMoreThan(TIMEOUT_SECONDS).seconds(),
                Click.on(AdminUsersPage.APPROVE_BUTTON_FOR.of(applicantEmail)),
                WaitUntil.the(AdminUsersPage.ACTION_SUCCESS, isVisible()).forNoMoreThan(TIMEOUT_SECONDS).seconds()
        );
    }
}
