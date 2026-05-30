package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.RegistrationPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): mensaje de confirmacion mostrado tras solicitar el
 * registro (la cuenta queda pendiente de aprobacion).
 */
public class RegistrationOutcome implements Question<String> {

    public static RegistrationOutcome message() {
        return new RegistrationOutcome();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(RegistrationPage.SUCCESS_ALERT).answeredBy(actor).trim();
    }
}
