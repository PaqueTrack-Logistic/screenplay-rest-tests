package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.ChooseOption;
import co.edu.udea.calidad.paquetrack.ui.interactions.ClickOn;
import co.edu.udea.calidad.paquetrack.ui.interactions.EnterText;
import co.edu.udea.calidad.paquetrack.ui.interactions.SetFieldValue;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.TrackingPage;
import co.edu.udea.calidad.paquetrack.ui.utils.UiTestData;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task de negocio (UI): el operador reporta un evento de tracking para un envio
 * desde el formulario web (id del envio + tipo de evento + ubicacion).
 */
public class RegisterATrackingEventThroughTheUI implements Task {

    private static final int TIMEOUT_SECONDS = 20;

    private final String eventType;
    private final String shipmentId;

    public RegisterATrackingEventThroughTheUI(String eventType, String shipmentId) {
        this.eventType = eventType;
        this.shipmentId = shipmentId;
    }

    public static RegisterATrackingEventThroughTheUI ofType(String eventType, String shipmentId) {
        return instrumented(RegisterATrackingEventThroughTheUI.class, eventType, shipmentId);
    }

    @Override
    @Step("{0} reporta un evento '#eventType' para el envio")
    public <T extends Actor> void performAs(T actor) {
        // Fecha del evento claramente en el pasado: el backend rechaza fechas
        // futuras y su reloj puede ir por detras del host; un margen amplio evita
        // falsos negativos por desfase de relojes.
        String occurredAt = LocalDateTime.now().minusDays(2)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        actor.attemptsTo(
                EnterText.of(shipmentId, TrackingPage.EVENT_SHIPMENT_ID),
                ChooseOption.value(eventType, TrackingPage.EVENT_TYPE),
                EnterText.of(UiTestData.EVENT_LOCATION, TrackingPage.EVENT_LOCATION),
                SetFieldValue.of(occurredAt, TrackingPage.EVENT_OCCURRED_AT),
                ClickOn.the(TrackingPage.REGISTER_EVENT_BUTTON),
                WaitUntil.the(TrackingPage.EVENT_SUCCESS, isVisible()).forNoMoreThan(TIMEOUT_SECONDS).seconds(),
                Scroll.to(TrackingPage.EVENT_SUCCESS)
        );
    }
}
