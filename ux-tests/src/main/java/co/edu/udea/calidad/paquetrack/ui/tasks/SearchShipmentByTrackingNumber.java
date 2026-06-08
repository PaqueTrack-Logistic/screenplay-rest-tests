package co.edu.udea.calidad.paquetrack.ui.tasks;

import co.edu.udea.calidad.paquetrack.ui.interactions.ClickOn;
import co.edu.udea.calidad.paquetrack.ui.interactions.EnterText;
import co.edu.udea.calidad.paquetrack.ui.interactions.Pause;
import co.edu.udea.calidad.paquetrack.ui.userinterfaces.ShipmentsPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task de negocio (UI): el operador busca un envio por su numero de guia desde la
 * tarjeta "Buscar envio" y espera a que aparezca el detalle.
 */
public class SearchShipmentByTrackingNumber implements Task {

    private static final int TIMEOUT_SECONDS = 20;

    private final String trackingNumber;

    public SearchShipmentByTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public static SearchShipmentByTrackingNumber of(String trackingNumber) {
        return instrumented(SearchShipmentByTrackingNumber.class, trackingNumber);
    }

    @Override
    @Step("{0} busca el envio con guia #trackingNumber")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterText.of(trackingNumber, ShipmentsPage.SEARCH_TRACKING_FIELD),
                Pause.toObserve(),                   // demo: ver la guia ingresada (configurable por UI_DELAY_MS)
                ClickOn.the(ShipmentsPage.SEARCH_BUTTON),
                WaitUntil.the(ShipmentsPage.SHIPMENT_DETAIL, isVisible()).forNoMoreThan(TIMEOUT_SECONDS).seconds()
        );
    }
}
