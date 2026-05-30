package co.edu.udea.calidad.paquetrack.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * UserInterface (Targets) de la pantalla de inicio de sesion del frontend.
 * Centraliza los localizadores: ninguna Task/Question los lleva inline.
 */
public class LoginPage {

    public static final Target EMAIL_FIELD =
            Target.the("email field").located(By.cssSelector("#login-email"));
    public static final Target PASSWORD_FIELD =
            Target.the("password field").located(By.cssSelector("#login-password"));
    public static final Target SIGN_IN_BUTTON =
            Target.the("sign-in button").located(By.cssSelector("button[type='submit']"));
    public static final Target ERROR_ALERT =
            Target.the("error alert").located(By.cssSelector(".alert-error"));

    private LoginPage() {
    }
}
