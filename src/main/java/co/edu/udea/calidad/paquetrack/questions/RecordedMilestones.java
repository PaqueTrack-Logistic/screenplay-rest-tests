package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

/**
 * Question de dominio: cuantos hitos hay registrados en el historial de
 * seguimiento. El historial es paginado, por eso se lee el total de elementos.
 */
public class RecordedMilestones implements Question<Integer> {

    public static RecordedMilestones inTheTrackingHistory() {
        return new RecordedMilestones();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        Integer total = LastResponse.received().answeredBy(actor).jsonPath().getInt("totalElements");
        return total == null ? 0 : total;
    }
}
