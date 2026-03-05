package arcn.eci.tddproject.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/arcn/eci/tddproject/features",
    glue = "arcn.eci.tddproject.steps",
    plugin = {
        "json:target/JSonReports/report.json",
        "html:target/HtmlReports/report.html",
        "junit:target/JUnitReports/report.xml"
    },
    monochrome = true
)
public class TestRunner {
}
