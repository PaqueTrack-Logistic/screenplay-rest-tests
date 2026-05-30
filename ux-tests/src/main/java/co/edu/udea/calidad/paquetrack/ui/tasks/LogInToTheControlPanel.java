package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.ClickOn;
import co.edu.udea.calidad.paquetrack.ui.interactions.EnterText;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.ControlPanel;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.LoginPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task de negocio (UI): el actor inicia sesion en el panel diligenciando el
 * formulario. Espera a que el panel autenticado cargue (el front es una SPA que
 * navega tras autenticar). La apertura de la pagina va en una precondicion.
 */
public class LogInToTheControlPanel implements Task {

    private static final int LOAD_TIMEOUT_SECONDS = 20;

    private final String email;
    private final String password;

    public LogInToTheControlPanel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LogInToTheControlPanel withCredentials(String email, String password) {
        return instrumented(LogInToTheControlPanel.class, email, password);
    }

    @Override
    @Step("{0} inicia sesion en el panel con #email")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterText.of(email, LoginPage.EMAIL_FIELD),
                EnterText.of(password, LoginPage.PASSWORD_FIELD),
                ClickOn.the(LoginPage.SIGN_IN_BUTTON),
                WaitUntil.the(ControlPanel.LOGOUT_BUTTON, isVisible()).forNoMoreThan(LOAD_TIMEOUT_SECONDS).seconds()
        );
    }
}
