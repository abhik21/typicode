package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class UsersStepDefinitions {
    private final prereq pr;
    private String uri;
    private RequestSpecification request;
    private Response response;

    public UsersStepDefinitions(prereq pr) {
        this.pr = pr;
    }

    @Given("^I have a service to request list of users$")
    public void iHaveAServiceToRequestListOfUsers() {
        request = given();
        uri = String.format("%s/%s", pr.url, "users");
        get(uri).then().assertThat().statusCode(200);

    }

    @When("^I request list of users$")
    public void iRequestListOfUsers() {
        response = request
                .get(uri);
        pr.response = response;
    }

}