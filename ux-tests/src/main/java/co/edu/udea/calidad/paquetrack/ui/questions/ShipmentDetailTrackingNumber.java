package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.ShipmentsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): numero de guia mostrado en el detalle del envio tras
 * buscarlo. Permite validar que la busqueda devolvio el envio correcto.
 */
public class ShipmentDetailTrackingNumber implements Question<String> {

    public static ShipmentDetailTrackingNumber shownInDetail() {
        return new ShipmentDetailTrackingNumber();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(ShipmentsPage.DETAIL_TRACKING_NUMBER).answeredBy(actor).trim();
    }
}
