package co.edu.udea.calidad.paquetrack.ui.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

/**
 * Question de dominio (UI): URL de la pantalla en la que se encuentra el actor.
 * Permite validar la navegacion (p. ej. que tras cerrar sesion vuelve al login).
 */
public class CurrentPageUrl implements Question<String> {

    public static CurrentPageUrl displayed() {
        return new CurrentPageUrl();
    }

    @Override
    public String answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
    }
}
