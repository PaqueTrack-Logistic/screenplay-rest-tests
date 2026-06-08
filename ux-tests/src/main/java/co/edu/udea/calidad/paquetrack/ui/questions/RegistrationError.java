package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.RegistrationPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): mensaje de error mostrado en el formulario de registro
 * cuando la solicitud es rechazada (contrasenas que no coinciden, correo ya usado...).
 */
public class RegistrationError implements Question<String> {

    public static RegistrationError message() {
        return new RegistrationError();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(RegistrationPage.ERROR_ALERT).answeredBy(actor).trim();
    }
}
