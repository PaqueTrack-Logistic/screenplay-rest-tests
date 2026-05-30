package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.ControlPanel;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): correo del usuario cuya sesion muestra el panel.
 * Permite validar que quien quedo autenticado es quien inicio sesion.
 */
public class SignedInUser implements Question<String> {

    public static SignedInUser email() {
        return new SignedInUser();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(ControlPanel.SIGNED_IN_EMAIL).answeredBy(actor).trim();
    }
}
