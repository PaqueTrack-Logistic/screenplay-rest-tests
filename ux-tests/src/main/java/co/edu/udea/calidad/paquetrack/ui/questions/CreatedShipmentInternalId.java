package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.ShipmentsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): identificador interno (UUID) del envio recien creado,
 * mostrado en la confirmacion. Sirve para encadenar el seguimiento del envio.
 */
public class CreatedShipmentInternalId implements Question<String> {

    public static CreatedShipmentInternalId shownOnScreen() {
        return new CreatedShipmentInternalId();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(ShipmentsPage.INTERNAL_ID).answeredBy(actor).trim();
    }
}
