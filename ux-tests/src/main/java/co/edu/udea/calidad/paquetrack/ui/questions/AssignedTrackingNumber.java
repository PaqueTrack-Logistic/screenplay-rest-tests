package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.ShipmentsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): numero de guia que la pantalla muestra tras crear el
 * envio. Formato de negocio PQ-AAAAMMDD-XXXXXX.
 */
public class AssignedTrackingNumber implements Question<String> {

    public static final String BUSINESS_FORMAT = "PQ-\\d{8}-[A-Z0-9]{6}";

    public static AssignedTrackingNumber shownOnScreen() {
        return new AssignedTrackingNumber();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(ShipmentsPage.ASSIGNED_TRACKING_NUMBER).answeredBy(actor).trim();
    }
}
