package co.edu.udea.calidad.paquetrack.stepdefinitions;

import co.edu.udea.calidad.paquetrack.questions.ReportedState;
import co.edu.udea.calidad.paquetrack.tasks.CheckCurrentTrackingStatus;
import co.edu.udea.calidad.paquetrack.tasks.RegisterATrackingEvent;
import co.edu.udea.calidad.paquetrack.utils.TestData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

/**
 * Step Definitions del CICLO DE VIDA de la entrega: a medida que el operador
 * logistico reporta eventos, el envio transita por su maquina de estados
 * (CREATED -> IN_TRANSIT -> OUT_FOR_DELIVERY -> DELIVERED). La propagacion entre
 * microservicios es asincrona, por eso el estado se valida "eventualmente".
 */
public class DeliveryLifecycleStepDefinitions {

    private Actor staff() {
        return OnStage.theActorCalled(TestData.ACTOR);
    }

    @When("the courier reports the {string} event for the shipment")
    public void theCourierReportsTheEvent(String eventType) {
        staff().attemptsTo(RegisterATrackingEvent.ofType(eventType));
    }

    @Then("the delivery state eventually becomes {string}")
    public void theDeliveryStateEventuallyBecomes(String expectedState) {
        staff().attemptsTo(CheckCurrentTrackingStatus.untilItBecomes(expectedState));
        staff().should(seeThat("the current delivery state", ReportedState.ofTheShipment(), is(expectedState)));
    }
}
