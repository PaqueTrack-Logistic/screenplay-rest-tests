package co.edu.udea.calidad.paquetrack.ui.stepdefinitions;

import co.edu.udea.calidad.paquetrack.ui.interactions.NavigateTo;
import co.edu.udea.calidad.paquetrack.ui.questions.SignedInUser;
import co.edu.udea.calidad.paquetrack.ui.tasks.LogInToTheControlPanel;
import co.edu.udea.calidad.paquetrack.ui.utils.UiConfig;
import co.edu.udea.calidad.paquetrack.ui.utils.UiTestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

/**
 * Step Definitions UI del dominio de ACCESO: solo traducen Gherkin de negocio
 * a Tasks/Questions; sin logica ni datos quemados.
 */
public class UiAuthenticationStepDefinitions {

    private Actor staff() {
        return OnStage.theActorCalled(UiTestData.ACTOR);
    }

    @Given("the operator is on the PaqueTrack login page")
    public void theOperatorIsOnTheLoginPage() {
        staff().attemptsTo(NavigateTo.the(UiConfig.loginUrl()));
    }

    @When("the administrator signs in with valid corporate credentials")
    public void theAdministratorSignsInWithValidCredentials() {
        staff().attemptsTo(
                LogInToTheControlPanel.withCredentials(UiTestData.ADMIN_EMAIL, UiTestData.ADMIN_PASSWORD));
    }

    @Then("the control panel shows the session for {string}")
    public void theControlPanelShowsTheSessionFor(String email) {
        staff().should(seeThat("the signed-in user shown in the panel", SignedInUser.email(), is(email)));
    }
}
