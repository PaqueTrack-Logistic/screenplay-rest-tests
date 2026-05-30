package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.List;

/**
 * Question de dominio: estados a los que ha transicionado el envio segun su
 * historial (campo "newStatus" de cada cambio registrado). Permite validar que
 * el historial refleja la progresion del envio.
 */
public class ShipmentStatusChanges implements Question<List<String>> {

    public static ShipmentStatusChanges recordedInTheHistory() {
        return new ShipmentStatusChanges();
    }

    @Override
    public List<String> answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).jsonPath().getList("newStatus");
    }
}
