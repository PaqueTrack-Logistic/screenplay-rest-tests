package co.edu.udea.calidad.paquetrack.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.List;

/**
 * Question de dominio: roles concedidos en la sesion de acceso (claim "roles"
 * del token emitido por el login). Permite validar el control de acceso (RBAC).
 */
public class GrantedRoles implements Question<List<String>> {

    public static GrantedRoles inTheAccessSession() {
        return new GrantedRoles();
    }

    @Override
    public List<String> answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).jsonPath().getList("roles");
    }
}
