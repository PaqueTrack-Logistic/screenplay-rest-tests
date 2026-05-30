package co.edu.udea.calidad.paquetrack.ui.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.WebElement;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction propia (la "menuda"): fija el valor de un campo controlado por
 * React (p. ej. datetime-local) usando el setter nativo y disparando el evento
 * 'input' para que React actualice su estado. Util cuando sendKeys no aplica.
 */
public class SetFieldValue implements Interaction {

    private static final String SCRIPT =
            "var el = arguments[0], v = arguments[1];"
            + "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
            + "setter.call(el, v);"
            + "el.dispatchEvent(new Event('input', { bubbles: true }));"
            + "el.dispatchEvent(new Event('change', { bubbles: true }));";

    private final String value;
    private final Target field;

    public SetFieldValue(String value, Target field) {
        this.value = value;
        this.field = field;
    }

    public static SetFieldValue of(String value, Target field) {
        return instrumented(SetFieldValue.class, value, field);
    }

    @Override
    @Step("{0} fija el valor del campo #field")
    public <T extends Actor> void performAs(T actor) {
        WebElement element = field.resolveFor(actor);
        BrowseTheWeb.as(actor).evaluateJavascript(SCRIPT, element, value);
    }
}
