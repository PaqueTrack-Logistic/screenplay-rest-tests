package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.ClickOn;
import co.edu.udea.calidad.paquetrack.ui.interactions.EnterText;
import co.edu.udea.calidad.paquetrack.ui.interactions.Pause;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.ShipmentsPage;
import co.edu.udea.calidad.paquetrack.ui.utils.UiTestData;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task de negocio (UI): el operador registra un envio diligenciando el
 * formulario web (remitente, destinatario, peso) y espera la confirmacion.
 */
public class RegisterAShipmentThroughTheUI implements Task {

    private static final int TIMEOUT_SECONDS = 20;

    private final String sender;
    private final String recipient;

    public RegisterAShipmentThroughTheUI(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public static RegisterAShipmentThroughTheUI from(String sender, String recipient) {
        return instrumented(RegisterAShipmentThroughTheUI.class, sender, recipient);
    }

    @Override
    @Step("{0} registra un envio de #sender a #recipient")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterText.of(sender, ShipmentsPage.SENDER_NAME),
                EnterText.of(UiTestData.SHIPMENT_ADDRESS, ShipmentsPage.SENDER_ADDRESS),
                EnterText.of(UiTestData.SENDER_CITY, ShipmentsPage.SENDER_CITY),
                EnterText.of(recipient, ShipmentsPage.RECIPIENT_NAME),
                EnterText.of(UiTestData.SHIPMENT_ADDRESS, ShipmentsPage.RECIPIENT_ADDRESS),
                EnterText.of(UiTestData.RECIPIENT_CITY, ShipmentsPage.RECIPIENT_CITY),
                EnterText.of(UiTestData.SHIPMENT_WEIGHT, ShipmentsPage.WEIGHT),
                Pause.toObserve(),                   // demo: ver el formulario diligenciado (configurable por UI_DELAY_MS)
                ClickOn.the(ShipmentsPage.CREATE_BUTTON),
                WaitUntil.the(ShipmentsPage.CREATION_SUCCESS, isVisible()).forNoMoreThan(TIMEOUT_SECONDS).seconds(),
                // Traer la confirmacion al area visible (evidencia visual inequivoca)
                Scroll.to(ShipmentsPage.CREATION_SUCCESS)
        );
    }
}
