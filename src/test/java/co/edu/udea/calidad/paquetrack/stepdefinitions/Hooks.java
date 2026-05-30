package co.edu.udea.calidad.paquetrack.stepdefinitions;

import co.edu.udea.calidad.paquetrack.utils.RestApiConfig;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

/**
 * Prepara el escenario antes de cada test: todos los actores pueden llamar
 * al API del gateway (URL leida de serenity.conf, no quemada).
 */
public class Hooks {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(
                Cast.whereEveryoneCan(CallAnApi.at(RestApiConfig.baseUrl()))
        );
    }
}
