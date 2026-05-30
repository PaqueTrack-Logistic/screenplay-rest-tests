package co.edu.udea.calidad.paquetrack.ui.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/tracking/tracking.feature",
        glue = "co.edu.udea.calidad.paquetrack.ui.stepdefinitions"
)
public class TrackingUxRunner {
}
