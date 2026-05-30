package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

/**
 * Question de dominio: estado del envio reportado por la plataforma (campo
 * "status"). Sirve para el envio recien creado y para el estado de seguimiento,
 * que comparten el ciclo de vida CREATED -> IN_TRANSIT -> ... -> DELIVERED.
 */
public class ReportedState implements Question<String> {

    public static ReportedState ofTheShipment() {
        return new ReportedState();
    }

    @Override
    public String answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).jsonPath().getString("status");
    }
}
