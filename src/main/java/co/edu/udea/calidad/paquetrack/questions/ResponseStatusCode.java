package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

/**
 * Question: codigo de estado HTTP de la ultima respuesta. Unica responsabilidad.
 */
public class ResponseStatusCode implements Question<Integer> {

    public static ResponseStatusCode value() {
        return new ResponseStatusCode();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).statusCode();
    }
}
