package co.edu.udea.calidad.paquetrack.ui.stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Prepara el escenario antes de cada test UI: cada actor puede navegar la web
 * (habilidad BrowseTheWeb con un navegador gestionado por Serenity). OnlineCast
 * abre y cierra el navegador por cada escenario (independencia).
 */
public class UiHooks {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }
}
