package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.Pause;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.AdminUsersPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task de negocio (UI): el administrador rechaza la solicitud del correo dado,
 * ubicando su fila en la tabla. Contraparte de {@link ApproveApplicantThroughTheUI}.
 */
public class RejectApplicantThroughTheUI implements Task {

    private static final int TIMEOUT_SECONDS = 20;

    private final String applicantEmail;

    public RejectApplicantThroughTheUI(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public static RejectApplicantThroughTheUI forApplicant(String applicantEmail) {
        return instrumented(RejectApplicantThroughTheUI.class, applicantEmail);
    }

    @Override
    @Step("{0} rechaza la solicitud de #applicantEmail")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AdminUsersPage.REJECT_BUTTON_FOR.of(applicantEmail), isVisible())
                        .forNoMoreThan(TIMEOUT_SECONDS).seconds(),
                Pause.toObserve(),                   // demo: ver la fila pendiente antes de rechazar (configurable por UI_DELAY_MS)
                Click.on(AdminUsersPage.REJECT_BUTTON_FOR.of(applicantEmail)),
                WaitUntil.the(AdminUsersPage.ACTION_SUCCESS, isVisible()).forNoMoreThan(TIMEOUT_SECONDS).seconds()
        );
    }
}
