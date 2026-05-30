package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: busca envios por remitente O por destinatario (el API exige solo uno).
 */
public class SearchShipments implements Task {

    private final String paramName;
    private final String value;

    public SearchShipments(String paramName, String value) {
        this.paramName = paramName;
        this.value = value;
    }

    public static SearchShipments bySender(String senderName) {
        return instrumented(SearchShipments.class, "senderName", senderName);
    }

    public static SearchShipments byRecipient(String recipientName) {
        return instrumented(SearchShipments.class, "recipientName", recipientName);
    }

    public static SearchShipments withBothParameters(String senderName, String recipientName) {
        // Caso invalido: ambos parametros -> 400
        return instrumented(SearchShipments.class, "BOTH:" + senderName, recipientName);
    }

    public static SearchShipments withoutParameters() {
        // Caso invalido: ningun parametro -> 400
        return instrumented(SearchShipments.class, "NONE", "");
    }

    @Override
    @Step("{0} busca envios (#paramName = #value)")
    public <T extends Actor> void performAs(T actor) {
        if ("NONE".equals(paramName)) {
            actor.attemptsTo(SendAuthenticatedGet.to(Endpoints.SHIPMENTS_SEARCH));
        } else if (paramName.startsWith("BOTH:")) {
            actor.attemptsTo(SendAuthenticatedGet.to(Endpoints.SHIPMENTS_SEARCH)
                    .withQuery("senderName", paramName.substring(5), "recipientName", value));
        } else {
            actor.attemptsTo(SendAuthenticatedGet.to(Endpoints.SHIPMENTS_SEARCH)
                    .withQuery(paramName, value));
        }
    }
}
