package co.edu.udea.calidad.paquetrack.stepdefinitions;

import co.edu.udea.calidad.paquetrack.questions.GrantedRoles;
import co.edu.udea.calidad.paquetrack.questions.RejectionReason;
import co.edu.udea.calidad.paquetrack.questions.ReportedState;
import co.edu.udea.calidad.paquetrack.questions.ResponseStatusCode;
import co.edu.udea.calidad.paquetrack.tasks.Authenticate;
import co.edu.udea.calidad.paquetrack.tasks.AuthenticateAsTheRegisteredUser;
import co.edu.udea.calidad.paquetrack.tasks.RegisterANewUser;
import co.edu.udea.calidad.paquetrack.tasks.RenewSession;
import co.edu.udea.calidad.paquetrack.tasks.ResumeSession;
import co.edu.udea.calidad.paquetrack.utils.TestData;
import co.edu.udea.calidad.paquetrack.utils.UniqueData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

/**
 * Step Definitions del dominio de ACCESO: registro de solicitantes, inicio de
 * sesion y las reglas de negocio que deciden a quien se le concede o niega el
 * acceso. Solo traducen Gherkin (negocio) a Tasks/Questions; sin logica ni datos.
 */
public class AccessStepDefinitions {

    private Actor staff() {
        return OnStage.theActorCalled(TestData.ACTOR);
    }

    // --- Contexto / actores (reutilizado como Background por otras features) ---

    @Given("a member of the logistics operation is using PaqueTrack")
    public void aMemberOfTheLogisticsOperationIsUsingPaqueTrack() {
        staff(); // pone al actor en escena; la URL base la provee serenity.conf
    }

    @Given("an administrator is signed in to PaqueTrack")
    public void anAdministratorIsSignedIn() {
        staff().attemptsTo(ResumeSession.forCredentials(TestData.ADMIN_EMAIL, TestData.ADMIN_PASSWORD));
    }

    @Given("a logistics operator is signed in to PaqueTrack")
    public void aLogisticsOperatorIsSignedIn() {
        staff().attemptsTo(ResumeSession.forCredentials(TestData.ADMIN_EMAIL, TestData.ADMIN_PASSWORD));
    }

    // --- Registro de nuevos solicitantes ---

    @Given("a new applicant has requested access to the platform")
    @When("a new applicant requests access to the platform")
    public void aNewApplicantRequestsAccess() {
        staff().attemptsTo(RegisterANewUser.withCredentials(UniqueData.email(), TestData.DEFAULT_PASSWORD));
    }

    @When("a person requests access using a password of length {int}")
    public void aPersonRequestsAccessUsingAPasswordOfLength(int length) {
        staff().attemptsTo(
                RegisterANewUser.withCredentials(UniqueData.email(), TestData.passwordOfLength(length)));
    }

    // --- Inicio de sesion ---

    @When("the administrator signs in with the corporate account")
    public void theAdministratorSignsIn() {
        staff().attemptsTo(Authenticate.withCredentials(TestData.ADMIN_EMAIL, TestData.ADMIN_PASSWORD));
    }

    @When("a person signs in with a valid corporate email but the wrong password")
    public void aPersonSignsInWithTheWrongPassword() {
        staff().attemptsTo(Authenticate.withCredentials(TestData.ADMIN_EMAIL, TestData.WRONG_PASSWORD));
    }

    @When("the applicant tries to sign in")
    public void theApplicantTriesToSignIn() {
        staff().attemptsTo(AuthenticateAsTheRegisteredUser.now());
    }

    @When("the user renews the session")
    public void theUserRenewsTheSession() {
        staff().attemptsTo(RenewSession.now());
    }

    // --- Reglas de negocio (validaciones via Questions) ---

    @Then("the platform grants access including the role {string}")
    public void thePlatformGrantsAccessIncludingTheRole(String role) {
        staff().should(seeThat("the granted roles", GrantedRoles.inTheAccessSession(), hasItem(role)));
    }

    @Then("the request is accepted and the account remains awaiting approval")
    public void theRequestIsAcceptedAndAwaitingApproval() {
        staff().should(seeThat("the account status",
                ReportedState.ofTheShipment(), is(TestData.ACCOUNT_PENDING)));
    }

    @Then("access is denied because the account is awaiting approval")
    public void accessIsDeniedBecauseAwaitingApproval() {
        staff().should(seeThat("the reason access was denied",
                RejectionReason.reportedByThePlatform(), is(TestData.REASON_PENDING_APPROVAL)));
    }

    @Then("access is denied because the credentials are invalid")
    public void accessIsDeniedBecauseCredentialsInvalid() {
        staff().should(seeThat("the reason access was denied",
                RejectionReason.reportedByThePlatform(), is(TestData.REASON_INVALID_CREDENTIALS)));
    }

    @Then("access is denied because the registration was rejected")
    public void accessIsDeniedBecauseRegistrationRejected() {
        staff().should(seeThat("the reason access was denied",
                RejectionReason.reportedByThePlatform(), is(TestData.REASON_REGISTRATION_REJECTED)));
    }

    @Then("the registration is accepted and the account is left awaiting approval")
    public void theRegistrationIsAccepted() {
        staff().should(seeThat("the account status",
                ReportedState.ofTheShipment(), is(TestData.ACCOUNT_PENDING)));
    }

    @Then("the registration is rejected for not meeting the minimum password length")
    public void theRegistrationIsRejectedForWeakPassword() {
        // La plataforma rechaza la solicitud (no crea cuenta) por violar la politica
        staff().should(seeThat("the registration was rejected",
                ResponseStatusCode.value(), is(400)));
    }
}
