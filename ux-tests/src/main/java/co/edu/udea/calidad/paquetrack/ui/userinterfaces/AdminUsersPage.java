package co.edu.udea.calidad.paquetrack.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/**
 * UserInterface (Targets) de la pantalla de administracion de solicitudes de
 * registro. El boton de aprobar/rechazar se ubica por la fila del correo.
 */
public class AdminUsersPage {

    public static final Target APPROVE_BUTTON_FOR = Target.the("approve button for {0}")
            .locatedBy("//tr[td[contains(normalize-space(.),'{0}')]]//button[contains(.,'Aprobar')]");
    public static final Target REJECT_BUTTON_FOR = Target.the("reject button for {0}")
            .locatedBy("//tr[td[contains(normalize-space(.),'{0}')]]//button[contains(.,'Rechazar')]");
    public static final Target ACTION_SUCCESS =
            Target.the("admin action result").locatedBy(".alert-success");

    private AdminUsersPage() {
    }
}
