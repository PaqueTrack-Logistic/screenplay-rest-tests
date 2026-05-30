package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.List;

/**
 * Question de dominio: cuantas filas (roles) trae el consolidado de usuarios por
 * rol. Cada fila agrupa un rol con su numero de usuarios.
 */
public class ReportedRoleStatistics implements Question<Integer> {

    public static ReportedRoleStatistics rowCount() {
        return new ReportedRoleStatistics();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        List<Object> rows = LastResponse.received().answeredBy(actor).jsonPath().getList("rows");
        return rows == null ? 0 : rows.size();
    }
}
