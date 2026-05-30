package co.edu.udea.calidad.paquetrack.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * UserInterface (Targets) de la pantalla de seguimiento (consulta de historial y
 * registro de eventos).
 */
public class TrackingPage {

    public static final Target EVENT_SHIPMENT_ID =
            Target.the("event shipment id").located(By.cssSelector("#event-shipment-id"));
    public static final Target EVENT_TYPE =
            Target.the("event type").located(By.cssSelector("select[name='eventType']"));
    public static final Target EVENT_LOCATION =
            Target.the("event location").located(By.cssSelector("input[name='location']"));
    public static final Target EVENT_OCCURRED_AT =
            Target.the("event date and time").located(By.cssSelector("input[name='occurredAt']"));
    public static final Target REGISTER_EVENT_BUTTON =
            Target.the("register event button").located(By.xpath("//button[contains(.,'Registrar evento')]"));
    public static final Target EVENT_SUCCESS =
            Target.the("event registration result").located(By.cssSelector(".alert-success"));

    private TrackingPage() {
    }
}
