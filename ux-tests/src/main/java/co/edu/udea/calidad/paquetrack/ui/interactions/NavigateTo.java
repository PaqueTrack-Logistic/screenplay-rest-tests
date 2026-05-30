package co.edu.udea.calidad.paquetrack.ui.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction propia (la "menuda"): abre una URL en el navegador.
 */
public class NavigateTo implements Interaction {

    private final String url;

    public NavigateTo(String url) {
        this.url = url;
    }

    public static NavigateTo the(String url) {
        return instrumented(NavigateTo.class, url);
    }

    @Override
    @Step("{0} navega a #url")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(url));
    }
}
