package co.edu.udea.calidad.paquetrack.ui.stepdefinitions;

import co.edu.udea.calidad.paquetrack.ui.interactions.NavigateTo;
import co.edu.udea.calidad.paquetrack.ui.questions.AssignedTrackingNumber;
import co.edu.udea.calidad.paquetrack.ui.questions.ShipmentDetailTrackingNumber;
import co.edu.udea.calidad.paquetrack.ui.tasks.RegisterAShipmentThroughTheUI;
import co.edu.udea.calidad.paquetrack.ui.tasks.SearchShipmentByTrackingNumber;
import co.edu.udea.calidad.paquetrack.ui.utils.UiConfig;
import co.edu.udea.calidad.paquetrack.ui.utils.UiTestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

/**
 * Step Definitions UI del dominio de ENVIOS: solo traducen Gherkin a Tasks/Questions.
 */
public class UiShipmentStepDefinitions {

    private static final String TRACKING_NUMBER = "registeredTrackingNumber";

    private Actor staff() {
        return OnStage.theActorCalled(UiTestData.ACTOR);
    }

    @Given("the operator is in the shipments area")
    public void theOperatorIsInTheShipmentsArea() {
        staff().attemptsTo(NavigateTo.the(UiConfig.shipmentsUrl()));
    }

    @When("the operator registers a shipment from {string} to {string}")
    public void theOperatorRegistersAShipment(String sender, String recipient) {
        staff().attemptsTo(RegisterAShipmentThroughTheUI.from(sender, recipient));
    }

    @Then("the shipment is registered with a tracking number")
    public void theShipmentIsRegisteredWithATrackingNumber() {
        staff().should(seeThat("the assigned tracking number",
                AssignedTrackingNumber.shownOnScreen(), matchesPattern(AssignedTrackingNumber.BUSINESS_FORMAT)));
    }

    @Given("the operator has registered a shipment from {string} to {string}")
    public void theOperatorHasRegisteredAShipment(String sender, String recipient) {
        staff().attemptsTo(RegisterAShipmentThroughTheUI.from(sender, recipient));
        staff().remember(TRACKING_NUMBER, AssignedTrackingNumber.shownOnScreen().answeredBy(staff()));
    }

    @When("the operator searches for that shipment by its tracking number")
    public void theOperatorSearchesForThatShipment() {
        staff().attemptsTo(SearchShipmentByTrackingNumber.of(staff().recall(TRACKING_NUMBER)));
    }

    @Then("the shipment detail is shown for that tracking number")
    public void theShipmentDetailIsShownForThatTrackingNumber() {
        String expected = staff().recall(TRACKING_NUMBER);
        staff().should(seeThat("the tracking number in the detail",
                ShipmentDetailTrackingNumber.shownInDetail(), is(expected)));
    }
}
