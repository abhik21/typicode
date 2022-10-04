import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = {"src/test/features"},
        glue = "steps",
        tags = "@test")
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}