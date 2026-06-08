package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.ClickOn;
import co.edu.udea.calidad.paquetrack.ui.interactions.EnterText;
import co.edu.udea.calidad.paquetrack.ui.interactions.Pause;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.TrackingPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task de negocio (UI): el operador consulta el historial de un envio por su id
 * interno desde la tarjeta "Ver historial". No asume resultado: la Question lee
 * el historial o el mensaje de error segun el caso.
 */
public class LookUpShipmentHistory implements Task {

    private static final int TIMEOUT_SECONDS = 20;

    private final String shipmentId;

    public LookUpShipmentHistory(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public static LookUpShipmentHistory of(String shipmentId) {
        return instrumented(LookUpShipmentHistory.class, shipmentId);
    }

    @Override
    @Step("{0} consulta el historial del envio #shipmentId")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterText.of(shipmentId, TrackingPage.HISTORY_SHIPMENT_ID),
                Pause.toObserve(),                   // demo: ver el id consultado (configurable por UI_DELAY_MS)
                ClickOn.the(TrackingPage.VIEW_HISTORY_BUTTON),
                // La consulta es asincrona: espera a que la plataforma pinte el
                // resultado (estado vacio) antes de que la Question lo lea.
                WaitUntil.the(TrackingPage.HISTORY_EMPTY_MESSAGE, isVisible()).forNoMoreThan(TIMEOUT_SECONDS).seconds()
        );
    }
}
