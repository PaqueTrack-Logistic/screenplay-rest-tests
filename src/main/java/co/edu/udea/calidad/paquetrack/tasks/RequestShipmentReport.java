package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.interactions.SendAuthenticatedGet;
import co.edu.udea.calidad.paquetrack.userinterfaces.Endpoints;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task: solicita el reporte de envios por rango de fechas (requiere ROLE_ADMIN/LOGISTICS).
 */
public class RequestShipmentReport implements Task {

    private final String from;
    private final String to;

    public RequestShipmentReport(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public static RequestShipmentReport between(String from, String to) {
        return instrumented(RequestShipmentReport.class, from, to);
    }

    @Override
    @Step("{0} solicita el reporte de envios del #from al #to")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SendAuthenticatedGet.to(Endpoints.SHIPMENTS_REPORT).withQuery("from", from, "to", to)
        );
    }
}
