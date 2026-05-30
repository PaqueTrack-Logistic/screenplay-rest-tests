package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedPost;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: registra un evento de tracking para un envio. La transicion de estado
 * resultante la procesa tracking-service de forma asincrona (RabbitMQ).
 */
public class RegisterATrackingEvent implements Task {

    private final String eventType;
    private final String explicitShipmentId;

    public RegisterATrackingEvent(String eventType, String explicitShipmentId) {
        this.eventType = eventType;
        this.explicitShipmentId = explicitShipmentId;
    }

    /** Usa el envio recordado (flujo E2E). */
    public static RegisterATrackingEvent ofType(String eventType) {
        return instrumented(RegisterATrackingEvent.class, eventType, null);
    }

    /** Usa un shipmentId explicito (p. ej. validar tipo de evento invalido). */
    public static RegisterATrackingEvent ofTypeForShipment(String eventType, String shipmentId) {
        return instrumented(RegisterATrackingEvent.class, eventType, shipmentId);
    }

    @Override
    @Step("{0} registra un evento de tracking '#eventType'")
    public <T extends Actor> void performAs(T actor) {
        String shipmentId = explicitShipmentId != null ? explicitShipmentId : actor.recall(Memory.SHIPMENT_ID);
        Map<String, Object> body = Map.of(
                "eventType", eventType,
                "location", "Centro logistico Medellin",
                "occurredAt", LocalDateTime.now().minusMinutes(1).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        actor.attemptsTo(
                SendAuthenticatedPost.to(Endpoints.trackingEvents(shipmentId)).withBody(body)
        );
    }
}
