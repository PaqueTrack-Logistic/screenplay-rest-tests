package co.edu.udea.calidad.paquetrack.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/shipments/shipments.feature",
        glue = "co.edu.udea.calidad.paquetrack.stepdefinitions"
)
public class ShipmentRunner {
}
