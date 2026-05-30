package co.edu.udea.calidad.paquetrack.ui.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction propia (la "menuda"): hace clic en un elemento.
 */
public class ClickOn implements Interaction {

    private final Target target;

    public ClickOn(Target target) {
        this.target = target;
    }

    public static ClickOn the(Target target) {
        return instrumented(ClickOn.class, target);
    }

    @Override
    @Step("{0} hace clic en #target")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(target));
    }
}
