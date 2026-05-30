package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.TrackingPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): mensaje de confirmacion tras registrar un evento de
 * tracking desde el formulario web.
 */
public class TrackingEventOutcome implements Question<String> {

    public static TrackingEventOutcome message() {
        return new TrackingEventOutcome();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(TrackingPage.EVENT_SUCCESS).answeredBy(actor).trim();
    }
}
