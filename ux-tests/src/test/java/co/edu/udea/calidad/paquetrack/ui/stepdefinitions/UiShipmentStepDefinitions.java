package co.edu.udea.calidad.paquetrack.ui.stepdefinitions;

import co.edu.udea.calidad.paquetrack.ui.interactions.NavigateTo;
import co.edu.udea.calidad.paquetrack.ui.questions.AssignedTrackingNumber;
import co.edu.udea.calidad.paquetrack.ui.tasks.RegisterAShipmentThroughTheUI;
import co.edu.udea.calidad.paquetrack.ui.utils.UiConfig;
import co.edu.udea.calidad.paquetrack.ui.utils.UiTestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.matchesPattern;

/**
 * Step Definitions UI del dominio de ENVIOS: solo traducen Gherkin a Tasks/Questions.
 */
public class UiShipmentStepDefinitions {

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
}
