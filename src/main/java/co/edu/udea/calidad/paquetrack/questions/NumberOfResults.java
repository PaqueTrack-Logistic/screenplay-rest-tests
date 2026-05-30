package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.List;

/**
 * Question de dominio: cuantos elementos devolvio la plataforma en una
 * respuesta de tipo coleccion (resultados de busqueda, catalogo de eventos).
 * Permite validar reglas de negocio como "la busqueda devuelve al menos un envio".
 */
public class NumberOfResults implements Question<Integer> {

    public static NumberOfResults returnedByThePlatform() {
        return new NumberOfResults();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        List<Object> items = LastResponse.received().answeredBy(actor).jsonPath().getList("$");
        return items == null ? 0 : items.size();
    }
}
