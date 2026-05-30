package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.ClickOn;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.ControlPanel;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.LoginPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task de negocio (UI): el actor cierra su sesion desde el panel y la plataforma
 * lo regresa a la pantalla de inicio de sesion.
 */
public class LogOut implements Task {

    private static final int TIMEOUT_SECONDS = 15;

    public static LogOut now() {
        return instrumented(LogOut.class);
    }

    @Override
    @Step("{0} cierra la sesion")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickOn.the(ControlPanel.LOGOUT_BUTTON),
                WaitUntil.the(LoginPage.EMAIL_FIELD, isVisible()).forNoMoreThan(TIMEOUT_SECONDS).seconds()
        );
    }
}
