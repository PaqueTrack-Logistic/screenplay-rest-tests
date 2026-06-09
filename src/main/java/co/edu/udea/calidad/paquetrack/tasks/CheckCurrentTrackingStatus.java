package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: consulta el estado actual de tracking del envio recordado.
 * Como la propagacion entre microservicios es asincrona (RabbitMQ), reintenta
 * hasta que el envio exista (HTTP 200) o, si se indica, hasta alcanzar un
 * estado esperado, dentro de un tiempo limite. Asi las pruebas son estables.
 */
public class CheckCurrentTrackingStatus implements Task {

    // Ventana de espera acotada para la consistencia eventual (RabbitMQ).
    // 80 intentos x 750 ms = 60 s: margen amplio para el "cold start" del consumidor
    // tras periodos de inactividad (arranque en frio del stack); en caliente retorna
    // en el primer intento, asi que no penaliza la corrida normal.
    private static final int MAX_ATTEMPTS = 80;
    private static final long DELAY_MS = 750L;

    private final String expectedStatus; // null => solo esperar a que exista (200)

    public CheckCurrentTrackingStatus(String expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    public static CheckCurrentTrackingStatus now() {
        return instrumented(CheckCurrentTrackingStatus.class, (String) null);
    }

    public static CheckCurrentTrackingStatus untilItBecomes(String expectedStatus) {
        return instrumented(CheckCurrentTrackingStatus.class, expectedStatus);
    }

    @Override
    @Step("{0} consulta el estado de tracking del envio")
    public <T extends Actor> void performAs(T actor) {
        String shipmentId = actor.recall(Memory.SHIPMENT_ID);
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            actor.attemptsTo(SendAuthenticatedGet.to(Endpoints.trackingCurrent(shipmentId)));
            Response response = LastResponse.received().answeredBy(actor);
            if (response.statusCode() == 200) {
                String status = response.jsonPath().getString("status");
                if (expectedStatus == null || expectedStatus.equals(status)) {
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
