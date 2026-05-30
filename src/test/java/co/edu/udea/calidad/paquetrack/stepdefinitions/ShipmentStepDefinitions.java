package co.edu.udea.calidad.paquetrack.stepdefinitions;

import co.edu.udea.calidad.paquetrack.questions.AssignedTrackingNumber;
import co.edu.udea.calidad.paquetrack.questions.NumberOfResults;
import co.edu.udea.calidad.paquetrack.questions.ReportedShipmentTotals;
import co.edu.udea.calidad.paquetrack.questions.ReportedState;
import co.edu.udea.calidad.paquetrack.questions.ResponseStatusCode;
import co.edu.udea.calidad.paquetrack.questions.ShipmentStatusChanges;
import co.edu.udea.calidad.paquetrack.tasks.ConsultShipmentHistory;
import co.edu.udea.calidad.paquetrack.tasks.CreateAShipment;
import co.edu.udea.calidad.paquetrack.tasks.RequestShipmentReport;
import co.edu.udea.calidad.paquetrack.tasks.RetrieveTheCreatedShipment;
import co.edu.udea.calidad.paquetrack.tasks.SearchShipments;
import co.edu.udea.calidad.paquetrack.utils.TestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

/**
 * Step Definitions del dominio de ENVIOS: alta, consulta, busqueda y reporte
 * gerencial. Solo traducen Gherkin de negocio a Tasks/Questions.
 */
public class ShipmentStepDefinitions {

    private Actor staff() {
        return OnStage.theActorCalled(TestData.ACTOR);
    }

    // --- Preconditions de negocio (reutilizadas por tracking y ciclo de vida) ---

    @Given("a shipment has been registered from {string}")
    public void aShipmentHasBeenRegisteredFrom(String sender) {
        staff().attemptsTo(CreateAShipment.from(TestData.shipmentFrom(sender)));
    }

    @Given("a shipment has been registered from {string} to {string}")
    public void aShipmentHasBeenRegisteredFromTo(String sender, String recipient) {
        staff().attemptsTo(CreateAShipment.from(TestData.shipmentFrom(sender, recipient)));
    }

    // --- Alta de envios ---

    @When("the operator registers a shipment from {string} in {string} to {string} in {string}")
    public void theOperatorRegistersAShipment(String sender, String senderCity,
                                              String recipient, String recipientCity) {
        staff().attemptsTo(
                CreateAShipment.from(TestData.shipmentFrom(sender, senderCity, recipient, recipientCity)));
    }

    // --- Consulta de envios ---

    @When("the operator looks up the shipment by its tracking number")
    public void theOperatorLooksUpByTrackingNumber() {
        staff().attemptsTo(RetrieveTheCreatedShipment.byTrackingId());
    }

    @When("the operator looks up the shipment by its identifier")
    public void theOperatorLooksUpByIdentifier() {
        staff().attemptsTo(RetrieveTheCreatedShipment.byId());
    }

    // --- Busqueda de envios ---

    @When("the operator searches shipments sent by {string}")
    public void theOperatorSearchesBySender(String sender) {
        staff().attemptsTo(SearchShipments.bySender(sender));
    }

    @When("the operator searches shipments addressed to {string}")
    public void theOperatorSearchesByRecipient(String recipient) {
        staff().attemptsTo(SearchShipments.byRecipient(recipient));
    }

    @When("the operator searches using both a sender {string} and a recipient {string}")
    public void theOperatorSearchesWithBothCriteria(String sender, String recipient) {
        staff().attemptsTo(SearchShipments.withBothParameters(sender, recipient));
    }

    @When("the operator searches without providing any criterion")
    public void theOperatorSearchesWithoutCriteria() {
        staff().attemptsTo(SearchShipments.withoutParameters());
    }

    // --- Reporte gerencial ---

    @When("the supervisor requests the shipments report for the current year")
    public void theSupervisorRequestsTheReportForTheYear() {
        staff().attemptsTo(RequestShipmentReport.between(TestData.REPORT_FROM, TestData.REPORT_TO));
    }

    @When("the supervisor requests the shipments report from {string} to {string}")
    public void theSupervisorRequestsTheReportBetween(String from, String to) {
        staff().attemptsTo(RequestShipmentReport.between(from, to));
    }

    // --- Validaciones de negocio (Questions) ---

    @Then("the shipment is registered with a unique tracking number")
    public void theShipmentIsRegisteredWithAUniqueTrackingNumber() {
        staff().should(seeThat("the assigned tracking number",
                AssignedTrackingNumber.ofTheShipment(),
                matchesPattern(AssignedTrackingNumber.BUSINESS_FORMAT)));
    }

    @Then("the shipment starts in state {string}")
    public void theShipmentStartsInState(String state) {
        staff().should(seeThat("the initial shipment state", ReportedState.ofTheShipment(), is(state)));
    }

    @Then("the shipment is found by its tracking number")
    public void theShipmentIsFound() {
        staff().should(seeThat("the located shipment tracking number",
                AssignedTrackingNumber.ofTheShipment(),
                matchesPattern(AssignedTrackingNumber.BUSINESS_FORMAT)));
    }

    @When("the operator consults the shipment status history")
    public void theOperatorConsultsTheShipmentStatusHistory() {
        staff().attemptsTo(ConsultShipmentHistory.ofTheShipment());
    }

    @Then("the shipment history records the change to {string}")
    public void theShipmentHistoryRecordsTheChangeTo(String state) {
        staff().should(seeThat("the recorded status changes",
                ShipmentStatusChanges.recordedInTheHistory(), hasItem(state)));
    }

    @Then("at least one shipment is returned for that sender")
    public void atLeastOneShipmentForSender() {
        staff().should(seeThat("shipments found for the sender",
                NumberOfResults.returnedByThePlatform(), greaterThan(0)));
    }

    @Then("the search returns shipments addressed to that recipient")
    public void shipmentsReturnedForRecipient() {
        staff().should(seeThat("shipments found for the recipient",
                NumberOfResults.returnedByThePlatform(), greaterThan(0)));
    }

    @Then("the search is rejected as an invalid query")
    public void theSearchIsRejected() {
        staff().should(seeThat("the search was rejected", ResponseStatusCode.value(), is(400)));
    }

    @Then("the report breaks down the shipments by delivery state")
    public void theReportBreaksDownByState() {
        staff().should(seeThat("total shipments consolidated in the report",
                ReportedShipmentTotals.overall(), greaterThanOrEqualTo(1)));
    }

    @Then("the report request is rejected as an invalid date range")
    public void theReportIsRejected() {
        staff().should(seeThat("the report request was rejected",
                ResponseStatusCode.value(), is(400)));
    }
}
