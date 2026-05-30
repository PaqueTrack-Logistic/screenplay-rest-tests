package co.edu.udea.calidad.paquetrack.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * UserInterface (Targets) del panel autenticado (barra lateral). Estos elementos
 * solo aparecen cuando hay una sesion activa.
 */
public class ControlPanel {

    public static final Target SIGNED_IN_EMAIL =
            Target.the("signed-in user email").located(By.cssSelector(".app-sidebar__email"));
    public static final Target LOGOUT_BUTTON =
            Target.the("logout button").located(By.cssSelector(".app-sidebar__logout"));

    private ControlPanel() {
    }
}
