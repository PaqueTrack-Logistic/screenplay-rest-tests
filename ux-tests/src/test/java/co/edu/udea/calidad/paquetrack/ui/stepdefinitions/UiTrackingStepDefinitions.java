package co.edu.udea.calidad.paquetrack.ui.stepdefinitions;

import co.edu.udea.calidad.paquetrack.ui.interactions.NavigateTo;
import co.edu.udea.calidad.paquetrack.ui.questions.CreatedShipmentInternalId;
import co.edu.udea.calidad.paquetrack.ui.questions.TrackingEventOutcome;
import co.edu.udea.calidad.paquetrack.ui.tasks.RegisterAShipmentThroughTheUI;
import co.edu.udea.calidad.paquetrack.ui.tasks.RegisterATrackingEventThroughTheUI;
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
 * Step Definitions UI del dominio de SEGUIMIENTO: registro de eventos desde la web.
 */
public class UiTrackingStepDefinitions {

    private static final String SHIPMENT_ID = "shipmentInternalId";

    private Actor staff() {
        return OnStage.theActorCalled(UiTestData.ACTOR);
    }

    @Given("the operator has just registered a shipment from {string} to {string}")
    public void theOperatorHasJustRegisteredAShipment(String sender, String recipient) {
        staff().attemptsTo(RegisterAShipmentThroughTheUI.from(sender, recipient));
        staff().remember(SHIPMENT_ID, CreatedShipmentInternalId.shownOnScreen().answeredBy(staff()));
    }

    @When("the operator reports a {string} tracking event for that shipment")
    public void theOperatorReportsATrackingEvent(String eventType) {
        staff().attemptsTo(
                NavigateTo.the(UiConfig.trackingUrl()),
                RegisterATrackingEventThroughTheUI.ofType(eventType, staff().recall(SHIPMENT_ID)));
    }

    @Then("the platform confirms the tracking event was registered")
    public void thePlatformConfirmsTheTrackingEventWasRegistered() {
        staff().should(seeThat("the tracking event confirmation",
                TrackingEventOutcome.message(), containsString("registrado")));
    }
}
