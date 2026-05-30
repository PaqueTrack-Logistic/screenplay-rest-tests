package co.edu.udea.calidad.paquetrack.ui.questions;

import co.edu.udea.calidad.paquetrack.ui.userinterfaces.AdminUsersPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question de dominio (UI): mensaje de confirmacion de una accion administrativa
 * (aprobar/rechazar) sobre una solicitud de registro.
 */
public class AdminActionOutcome implements Question<String> {

    public static AdminActionOutcome message() {
        return new AdminActionOutcome();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(AdminUsersPage.ACTION_SUCCESS).answeredBy(actor).trim();
    }
}
