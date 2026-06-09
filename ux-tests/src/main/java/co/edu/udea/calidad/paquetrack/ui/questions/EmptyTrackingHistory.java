package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.TrackingPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): mensaje de estado vacio que muestra el historial
 * cuando el envio consultado no tiene eventos (p. ej. un id inexistente).
 */
public class EmptyTrackingHistory implements Question<String> {

    public static EmptyTrackingHistory message() {
        return new EmptyTrackingHistory();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(TrackingPage.HISTORY_EMPTY_MESSAGE).answeredBy(actor).trim();
    }
}
