package co.edu.udea.calidad.paquetrack.stepdefinitions;

import co.edu.udea.calidad.paquetrack.questions.NumberOfResults;
import co.edu.udea.calidad.paquetrack.questions.RecordedMilestones;
import co.edu.udea.calidad.paquetrack.questions.ResponseStatusCode;
import co.edu.udea.calidad.paquetrack.tasks.ConsultEventTypes;
import co.edu.udea.calidad.paquetrack.tasks.ConsultTrackingHistory;
import co.edu.udea.calidad.paquetrack.tasks.RegisterATrackingEvent;
import co.edu.udea.calidad.paquetrack.utils.TestData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

/**
 * Step Definitions del dominio de SEGUIMIENTO: catalogo de eventos soportados y
 * registro de eventos. Solo traducen Gherkin de negocio a Tasks/Questions.
 */
public class TrackingStepDefinitions {

    private Actor staff() {
        return OnStage.theActorCalled(TestData.ACTOR);
    }

    @When("the operator consults the catalog of tracking events")
    public void theOperatorConsultsTheCatalog() {
        staff().attemptsTo(ConsultEventTypes.catalog());
    }

    @When("the operator reports an unsupported {string} tracking event")
    public void theOperatorReportsAnUnsupportedEvent(String eventType) {
        staff().attemptsTo(RegisterATrackingEvent.ofType(eventType));
    }

    @When("the operator consults the tracking history of the shipment")
    public void theOperatorConsultsTheTrackingHistory() {
        staff().attemptsTo(ConsultTrackingHistory.ofTheShipment());
    }

    @Then("the catalog lists the supported tracking events")
    public void theCatalogListsTheSupportedEvents() {
        staff().should(seeThat("number of supported tracking events",
                NumberOfResults.returnedByThePlatform(), greaterThan(0)));
    }

    @Then("the tracking event is rejected as unsupported")
    public void theTrackingEventIsRejected() {
        staff().should(seeThat("the tracking event was rejected",
                ResponseStatusCode.value(), is(400)));
    }

    @Then("the tracking history records at least one milestone")
    public void theTrackingHistoryRecordsMilestones() {
        staff().should(seeThat("milestones recorded in the tracking history",
                RecordedMilestones.inTheTrackingHistory(), greaterThanOrEqualTo(1)));
    }
}
