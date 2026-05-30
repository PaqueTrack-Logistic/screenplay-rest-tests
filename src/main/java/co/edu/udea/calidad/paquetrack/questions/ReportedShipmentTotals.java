package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

/**
 * Question de dominio: total de envios consolidado en el reporte gerencial.
 * El reporte desglosa por estado (creados, en transito, entregados, etc.) y
 * publica el total general; esta pregunta expone ese consolidado de negocio.
 */
public class ReportedShipmentTotals implements Question<Integer> {

    public static ReportedShipmentTotals overall() {
        return new ReportedShipmentTotals();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        Integer total = LastResponse.received().answeredBy(actor).jsonPath().getInt("totalGeneral");
        return total == null ? 0 : total;
    }
}
