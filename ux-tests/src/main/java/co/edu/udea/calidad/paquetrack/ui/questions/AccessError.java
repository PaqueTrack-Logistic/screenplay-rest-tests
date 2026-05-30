package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.LoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): mensaje de error mostrado en la pantalla de login
 * cuando el acceso es rechazado.
 */
public class AccessError implements Question<String> {

    public static AccessError message() {
        return new AccessError();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(LoginPage.ERROR_ALERT).answeredBy(actor).trim();
    }
}
