package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.List;

/**
 * Question de dominio: roles que la plataforma ofrece para asignar a un
 * solicitante aprobado (catalogo de roles del control de acceso).
 */
public class AssignableRoles implements Question<List<String>> {

    public static AssignableRoles offeredByThePlatform() {
        return new AssignableRoles();
    }

    @Override
    public List<String> answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).jsonPath().getList("roles");
    }
}
