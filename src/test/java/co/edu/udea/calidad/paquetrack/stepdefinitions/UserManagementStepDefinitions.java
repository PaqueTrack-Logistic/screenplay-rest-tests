package co.edu.udea.calidad.paquetrack.stepdefinitions;

import co.edu.udea.calidad.paquetrack.questions.AssignableRoles;
import co.edu.udea.calidad.paquetrack.questions.ReportedRoleStatistics;
import co.edu.udea.calidad.paquetrack.questions.ResponseStatusCode;
import co.edu.udea.calidad.paquetrack.tasks.ApproveTheRegisteredUser;
import co.edu.udea.calidad.paquetrack.tasks.AuthenticateAsTheRegisteredUser;
import co.edu.udea.calidad.paquetrack.tasks.ConsultAssignableRoles;
import co.edu.udea.calidad.paquetrack.tasks.ConsultUserStatisticsByRole;
import co.edu.udea.calidad.paquetrack.tasks.ListPendingUsers;
import co.edu.udea.calidad.paquetrack.tasks.RejectTheRegisteredUser;
import co.edu.udea.calidad.paquetrack.utils.TestData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

/**
 * Step Definitions del dominio de ADMINISTRACION DE ACCESO: aprobacion de
 * solicitantes y control de acceso por rol (RBAC). Solo traducen Gherkin.
 */
public class UserManagementStepDefinitions {

    private Actor staff() {
        return OnStage.theActorCalled(TestData.ACTOR);
    }

    @When("the administrator approves the applicant as an operator")
    public void theAdministratorApprovesAsOperator() {
        staff().attemptsTo(ApproveTheRegisteredUser.asRole(TestData.ROLE_OPERATOR));
    }

    @When("the approved applicant signs in")
    public void theApprovedApplicantSignsIn() {
        staff().attemptsTo(AuthenticateAsTheRegisteredUser.now());
    }

    @When("the administrator rejects the applicant")
    public void theAdministratorRejectsTheApplicant() {
        staff().attemptsTo(RejectTheRegisteredUser.now());
    }

    @When("the administrator reviews the registrations awaiting approval")
    public void theAdministratorReviewsPendingRegistrations() {
        staff().attemptsTo(ListPendingUsers.request());
    }

    @When("the administrator consults the assignable roles")
    public void theAdministratorConsultsAssignableRoles() {
        staff().attemptsTo(ConsultAssignableRoles.available());
    }

    @When("the administrator consults the user statistics by role")
    public void theAdministratorConsultsUserStatistics() {
        staff().attemptsTo(ConsultUserStatisticsByRole.now());
    }

    @When("an unauthenticated visitor tries to review the registrations awaiting approval")
    public void anUnauthenticatedVisitorTriesToReviewPending() {
        staff().attemptsTo(ListPendingUsers.request());
    }

    @When("the operator tries to review the registrations awaiting approval")
    public void theOperatorTriesToReviewPending() {
        staff().attemptsTo(ListPendingUsers.request());
    }

    @Then("the platform returns the list of applicants awaiting approval")
    public void thePlatformReturnsThePendingList() {
        staff().should(seeThat("the pending registrations were returned",
                ResponseStatusCode.value(), is(200)));
    }

    @Then("the operation is rejected because authentication is required")
    public void theOperationIsRejectedAuthenticationRequired() {
        staff().should(seeThat("authentication was required",
                ResponseStatusCode.value(), is(401)));
    }

    @Then("the operation is forbidden for the operator role")
    public void theOperationIsForbiddenForOperator() {
        staff().should(seeThat("the operator role was not authorized",
                ResponseStatusCode.value(), is(403)));
    }

    @Then("the assignable roles include {string}")
    public void theAssignableRolesInclude(String role) {
        staff().should(seeThat("the assignable roles", AssignableRoles.offeredByThePlatform(), hasItem(role)));
    }

    @Then("the statistics break down the users by role")
    public void theStatisticsBreakDownUsersByRole() {
        staff().should(seeThat("number of role groups in the statistics",
                ReportedRoleStatistics.rowCount(), greaterThanOrEqualTo(1)));
    }
}
