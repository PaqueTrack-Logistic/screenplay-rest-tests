package co.edu.udea.calidad.paquetrack.ui.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction propia (la "menuda"): escribe un valor en un campo del formulario.
 */
public class EnterText implements Interaction {

    private final String value;
    private final Target field;

    public EnterText(String value, Target field) {
        this.value = value;
        this.field = field;
    }

    public static EnterText of(String value, Target field) {
        return instrumented(EnterText.class, value, field);
    }

    @Override
    @Step("{0} escribe en el campo #field")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(value).into(field));
    }
}
