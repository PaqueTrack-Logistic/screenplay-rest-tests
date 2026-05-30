package co.edu.udea.calidad.paquetrack.ui.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction propia (la "menuda"): elige una opcion de una lista desplegable
 * por su valor.
 */
public class ChooseOption implements Interaction {

    private final String value;
    private final Target dropdown;

    public ChooseOption(String value, Target dropdown) {
        this.value = value;
        this.dropdown = dropdown;
    }

    public static ChooseOption value(String value, Target dropdown) {
        return instrumented(ChooseOption.class, value, dropdown);
    }

    @Override
    @Step("{0} selecciona '#value' en #dropdown")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(SelectFromOptions.byValue(value).from(dropdown));
    }
}
