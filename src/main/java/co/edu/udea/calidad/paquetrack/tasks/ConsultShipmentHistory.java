package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: consulta el historial de cambios de estado del envio recordado. El
 * shipment-service actualiza ese historial de forma asincrona al consumir los
 * eventos de tracking (RabbitMQ); por eso reintenta hasta que haya registros.
 */
public class ConsultShipmentHistory implements Task {

    private static final int MAX_ATTEMPTS = 20;
    private static final long DELAY_MS = 750L;

    public static ConsultShipmentHistory ofTheShipment() {
        return instrumented(ConsultShipmentHistory.class);
    }

    @Override
    @Step("{0} consulta el historial de estados del envio")
    public <T extends Actor> void performAs(T actor) {
        String path = Endpoints.shipmentHistory(actor.recall(Memory.SHIPMENT_ID));
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            actor.attemptsTo(SendAuthenticatedGet.to(path));
            Response response = LastResponse.received().answeredBy(actor);
            if (response.statusCode() == 200) {
                List<Object> entries = response.jsonPath().getList("$");
                if (entries != null && !entries.isEmpty()) {
                    return;
                }
            }
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
