package co.edu.udea.calidad.paquetrack.ui.stepdefinitions;

import co.edu.udea.calidad.paquetrack.ui.interactions.NavigateTo;
import co.edu.udea.calidad.paquetrack.ui.questions.AccessError;
import co.edu.udea.calidad.paquetrack.ui.questions.CurrentPageUrl;
import co.edu.udea.calidad.paquetrack.ui.questions.SignedInUser;
import co.edu.udea.calidad.paquetrack.ui.tasks.LogInToTheControlPanel;
import co.edu.udea.calidad.paquetrack.ui.tasks.LogOut;
import co.edu.udea.calidad.paquetrack.ui.tasks.RequestAccessThroughTheUI;
import co.edu.udea.calidad.paquetrack.ui.tasks.SignIn;
import co.edu.udea.calidad.paquetrack.ui.utils.UiConfig;
import co.edu.udea.calidad.paquetrack.ui.utils.UiTestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;

/**
 * Step Definitions UI del dominio de ACCESO: solo traducen Gherkin de negocio
 * a Tasks/Questions; sin logica ni datos quemados.
 */
public class UiAuthenticationStepDefinitions {

    private static final String PENDING_EMAIL = "pendingApplicantEmail";

    private Actor staff() {
        return OnStage.theActorCalled(UiTestData.ACTOR);
    }

    @Given("the operator is on the PaqueTrack login page")
    public void theOperatorIsOnTheLoginPage() {
        staff().attemptsTo(NavigateTo.the(UiConfig.loginUrl()));
    }

    @Given("an operator has signed in to PaqueTrack")
    public void anOperatorHasSignedIn() {
        staff().attemptsTo(
                NavigateTo.the(UiConfig.loginUrl()),
                LogInToTheControlPanel.withCredentials(UiTestData.ADMIN_EMAIL, UiTestData.ADMIN_PASSWORD));
    }

    @When("the administrator signs in with valid corporate credentials")
    public void theAdministratorSignsInWithValidCredentials() {
        staff().attemptsTo(
                LogInToTheControlPanel.withCredentials(UiTestData.ADMIN_EMAIL, UiTestData.ADMIN_PASSWORD));
    }

    @When("a person signs in with an incorrect password")
    public void aPersonSignsInWithAnIncorrectPassword() {
        staff().attemptsTo(SignIn.withCredentials(UiTestData.ADMIN_EMAIL, UiTestData.WRONG_PASSWORD));
    }

    @Given("a new applicant has just requested access from the web")
    public void aNewApplicantHasJustRequestedAccess() {
        String email = UiTestData.uniqueEmail();
        staff().remember(PENDING_EMAIL, email);
        staff().attemptsTo(
                NavigateTo.the(UiConfig.registerUrl()),
                RequestAccessThroughTheUI.withCredentials(email, UiTestData.NEW_USER_PASSWORD));
    }

    @When("that applicant tries to sign in")
    public void thatApplicantTriesToSignIn() {
        staff().attemptsTo(
                NavigateTo.the(UiConfig.loginUrl()),
                SignIn.withCredentials(staff().recall(PENDING_EMAIL), UiTestData.NEW_USER_PASSWORD));
    }

    @When("the user logs out")
    public void theUserLogsOut() {
        staff().attemptsTo(LogOut.now());
    }

    @Then("the control panel shows the session for {string}")
    public void theControlPanelShowsTheSessionFor(String email) {
        staff().should(seeThat("the signed-in user shown in the panel", SignedInUser.email(), is(email)));
    }

    @Then("access is refused with a message on screen")
    public void accessIsRefusedWithAMessage() {
        staff().should(seeThat("the access error message", AccessError.message(), is(not(emptyString()))));
    }

    @Then("the platform returns to the login page")
    public void thePlatformReturnsToTheLoginPage() {
        staff().should(seeThat("the current page", CurrentPageUrl.displayed(), containsString("/login")));
    }
}
