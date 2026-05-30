package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

/**
 * Question de dominio: numero de guia (tracking number) asignado al envio.
 * Regla de negocio: formato PQ-AAAAMMDD-XXXXXX (fecha + sufijo unico).
 */
public class AssignedTrackingNumber implements Question<String> {

    public static final String BUSINESS_FORMAT = "PQ-\\d{8}-[A-Z0-9]{6}";

    public static AssignedTrackingNumber ofTheShipment() {
        return new AssignedTrackingNumber();
    }

    @Override
    public String answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).jsonPath().getString("trackingId");
    }
}
