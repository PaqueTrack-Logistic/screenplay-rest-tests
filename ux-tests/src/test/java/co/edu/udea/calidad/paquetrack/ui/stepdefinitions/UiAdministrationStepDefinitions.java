package co.edu.udea.calidad.paquetrack.ui.stepdefinitions;

import co.edu.udea.calidad.paquetrack.ui.interactions.NavigateTo;
import co.edu.udea.calidad.paquetrack.ui.questions.AdminActionOutcome;
import co.edu.udea.calidad.paquetrack.ui.tasks.ApproveApplicantThroughTheUI;
import co.edu.udea.calidad.paquetrack.ui.tasks.RequestAccessThroughTheUI;
import co.edu.udea.calidad.paquetrack.ui.utils.UiConfig;
import co.edu.udea.calidad.paquetrack.ui.utils.UiTestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.containsString;

/**
 * Step Definitions UI del dominio de ADMINISTRACION: registro de un solicitante
 * y su aprobacion por un administrador desde el panel (RBAC visual).
 */
public class UiAdministrationStepDefinitions {

    private static final String APPLICANT_EMAIL = "applicantEmail";

    private Actor staff() {
        return OnStage.theActorCalled(UiTestData.ACTOR);
    }

    @Given("a new applicant has requested access from the web")
    public void aNewApplicantHasRequestedAccessFromTheWeb() {
        String email = UiTestData.uniqueEmail();
        staff().remember(APPLICANT_EMAIL, email);
        staff().attemptsTo(
                NavigateTo.the(UiConfig.registerUrl()),
                RequestAccessThroughTheUI.withCredentials(email, UiTestData.NEW_USER_PASSWORD));
    }

    @Given("the administrator opens the pending registrations")
    public void theAdministratorOpensThePendingRegistrations() {
        staff().attemptsTo(NavigateTo.the(UiConfig.adminUsersUrl()));
    }

    @When("the administrator approves that applicant")
    public void theAdministratorApprovesThatApplicant() {
        staff().attemptsTo(ApproveApplicantThroughTheUI.forApplicant(staff().recall(APPLICANT_EMAIL)));
    }

    @Then("the platform confirms the applicant was approved")
    public void thePlatformConfirmsTheApplicantWasApproved() {
        staff().should(seeThat("the approval confirmation", AdminActionOutcome.message(), containsString("aprobado")));
    }
}
