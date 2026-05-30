package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedPost;
import co.edu.udea.calidad.paquetrack.models.ShipmentRequest;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import co.edu.udea.calidad.paquetrack.utils.Memory;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task de negocio: el actor crea un envio. Recuerda id y trackingId del envio
 * creado para encadenar consultas/tracking en flujos E2E.
 */
public class CreateAShipment implements Task {

    private final ShipmentRequest request;

    public CreateAShipment(ShipmentRequest request) {
        this.request = request;
    }

    public static CreateAShipment from(ShipmentRequest request) {
        return instrumented(CreateAShipment.class, request);
    }

    @Override
    @Step("{0} crea un envio")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(SendAuthenticatedPost.to(Endpoints.SHIPMENTS).withBody(request));
        Response response = LastResponse.received().answeredBy(actor);
        if (response.statusCode() == 201) {
            actor.remember(Memory.SHIPMENT_ID, response.jsonPath().getString("id"));
            actor.remember(Memory.TRACKING_ID, response.jsonPath().getString("trackingId"));
        }
    }
}
