package co.edu.udea.calidad.paquetrack.ui.stepdefinitions;

import co.edu.udea.calidad.paquetrack.ui.interactions.NavigateTo;
import co.edu.udea.calidad.paquetrack.ui.questions.RegistrationError;
import co.edu.udea.calidad.paquetrack.ui.questions.RegistrationOutcome;
import co.edu.udea.calidad.paquetrack.ui.tasks.RequestAccessThroughTheUI;
import co.edu.udea.calidad.paquetrack.ui.tasks.SubmitAccessRequest;
import co.edu.udea.calidad.paquetrack.ui.utils.UiConfig;
import co.edu.udea.calidad.paquetrack.ui.utils.UiTestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;

/**
 * Step Definitions UI del dominio de REGISTRO: solo traducen Gherkin a Tasks/Questions.
 */
public class UiRegistrationStepDefinitions {

    private Actor staff() {
        return OnStage.theActorCalled(UiTestData.ACTOR);
    }

    @Given("the visitor is on the PaqueTrack registration page")
    public void theVisitorIsOnTheRegistrationPage() {
        staff().attemptsTo(NavigateTo.the(UiConfig.registerUrl()));
    }

    @When("a new applicant requests access through the form")
    public void aNewApplicantRequestsAccessThroughTheForm() {
        staff().attemptsTo(
                RequestAccessThroughTheUI.withCredentials(UiTestData.uniqueEmail(), UiTestData.NEW_USER_PASSWORD));
    }

    @Then("the platform confirms the request is pending approval")
    public void thePlatformConfirmsTheRequestIsPending() {
        staff().should(seeThat("the registration confirmation message",
                RegistrationOutcome.message(), is(not(emptyString()))));
    }

    @When("a new applicant submits the access form with mismatched passwords")
    public void aNewApplicantSubmitsWithMismatchedPasswords() {
        staff().attemptsTo(SubmitAccessRequest.with(
                UiTestData.uniqueEmail(), UiTestData.NEW_USER_PASSWORD, UiTestData.WRONG_PASSWORD));
    }

    @When("a person requests access with an email that is already registered")
    public void aPersonRequestsAccessWithAnAlreadyRegisteredEmail() {
        staff().attemptsTo(SubmitAccessRequest.with(
                UiTestData.ADMIN_EMAIL, UiTestData.NEW_USER_PASSWORD, UiTestData.NEW_USER_PASSWORD));
    }

    @Then("the platform refuses the request with a validation message")
    public void thePlatformRefusesTheRequestWithAMessage() {
        staff().should(seeThat("the registration error message",
                RegistrationError.message(), is(not(emptyString()))));
    }
}
