package co.edu.udea.calidad.paquetrack.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * UserInterface (Targets) de la pantalla de gestion de envios.
 */
public class ShipmentsPage {

    public static final Target SENDER_NAME =
            Target.the("sender name").located(By.cssSelector("input[name='senderName']"));
    public static final Target SENDER_ADDRESS =
            Target.the("sender address").located(By.cssSelector("input[name='senderAddress']"));
    public static final Target SENDER_CITY =
            Target.the("sender city").located(By.cssSelector("input[name='senderCity']"));
    public static final Target RECIPIENT_NAME =
            Target.the("recipient name").located(By.cssSelector("input[name='recipientName']"));
    public static final Target RECIPIENT_ADDRESS =
            Target.the("recipient address").located(By.cssSelector("input[name='recipientAddress']"));
    public static final Target RECIPIENT_CITY =
            Target.the("recipient city").located(By.cssSelector("input[name='recipientCity']"));
    public static final Target WEIGHT =
            Target.the("weight").located(By.cssSelector("input[name='weightKg']"));
    public static final Target CREATE_BUTTON =
            Target.the("create shipment button").located(By.xpath("//button[contains(.,'Crear env')]"));

    public static final Target CREATION_SUCCESS =
            Target.the("creation success message").located(By.cssSelector(".alert-success"));
    public static final Target ASSIGNED_TRACKING_NUMBER =
            Target.the("assigned tracking number").located(By.cssSelector(".tracking-id"));
    public static final Target INTERNAL_ID =
            Target.the("internal shipment id").located(By.cssSelector("small.code-chip"));

    private ShipmentsPage() {
    }
}
