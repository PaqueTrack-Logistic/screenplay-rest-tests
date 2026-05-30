package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: consulta el envio creado (por su id recordado) o por tracking id.
 */
public class RetrieveTheCreatedShipment implements Task {

    private final boolean byTracking;

    public RetrieveTheCreatedShipment(boolean byTracking) {
        this.byTracking = byTracking;
    }

    public static RetrieveTheCreatedShipment byId() {
        return instrumented(RetrieveTheCreatedShipment.class, false);
    }

    public static RetrieveTheCreatedShipment byTrackingId() {
        return instrumented(RetrieveTheCreatedShipment.class, true);
    }

    @Override
    @Step("{0} consulta el envio creado")
    public <T extends Actor> void performAs(T actor) {
        String path = byTracking
                ? Endpoints.shipmentByTracking(actor.recall(Memory.TRACKING_ID))
                : Endpoints.shipmentById(actor.recall(Memory.SHIPMENT_ID));
        actor.attemptsTo(SendAuthenticatedGet.to(path));
    }
}
