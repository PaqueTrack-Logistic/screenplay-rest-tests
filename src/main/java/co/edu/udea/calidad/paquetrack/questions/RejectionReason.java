package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

/**
 * Question de dominio: motivo de negocio por el que la plataforma rechazo la
 * operacion (codigo de error funcional: credenciales invalidas, cuenta
 * pendiente de aprobacion, validacion, etc.).
 */
public class RejectionReason implements Question<String> {

    public static RejectionReason reportedByThePlatform() {
        return new RejectionReason();
    }

    @Override
    public String answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).jsonPath().getString("errorCode");
    }
}
