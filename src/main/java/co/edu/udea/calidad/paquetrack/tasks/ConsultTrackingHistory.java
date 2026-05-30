package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: consulta el historial de eventos de tracking del envio recordado
 * (bitacora de hitos: estado anterior, estado nuevo, ubicacion, fecha).
 */
public class ConsultTrackingHistory implements Task {

    public static ConsultTrackingHistory ofTheShipment() {
        return instrumented(ConsultTrackingHistory.class);
    }

    @Override
    @Step("{0} consulta el historial de seguimiento del envio")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SendAuthenticatedGet.to(Endpoints.trackingHistory(actor.recall(Memory.SHIPMENT_ID)))
        );
    }
}
