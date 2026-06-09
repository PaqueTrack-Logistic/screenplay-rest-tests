package co.edu.udea.calidad.paquetrack.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * UserInterface (Targets) de la pantalla de solicitud de registro del frontend.
 */
public class RegistrationPage {

    public static final Target EMAIL_FIELD =
            Target.the("email field").located(By.cssSelector("#reg-email"));
    public static final Target PASSWORD_FIELD =
            Target.the("password field").located(By.cssSelector("#reg-password"));
    public static final Target CONFIRM_FIELD =
            Target.the("confirm password field").located(By.cssSelector("#reg-confirm"));
    public static final Target SUBMIT_BUTTON =
            Target.the("request access button").located(By.cssSelector("button[type='submit']"));
    public static final Target SUCCESS_ALERT =
            Target.the("success message").located(By.cssSelector(".alert-success"));
    public static final Target ERROR_ALERT =
            Target.the("validation error message").located(By.cssSelector(".alert-error"));

    private RegistrationPage() {
    }
}
