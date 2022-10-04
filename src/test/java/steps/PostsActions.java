package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import pojo.Post;

import static io.restassured.RestAssured.given;

public class NewPostStepDefinitions {
    private final prereq pr;
    private String uri;

    public NewPostStepDefinitions(prereq pr) {
        this.pr = pr;
    }

    @Given("^I have an end point posts$")
    public void iHaveAnEndPointPosts() {
        uri = String.format("%s/%s", pr.url, "posts");
    }

    @When("^I post with title \"([^\"]*)\" and author \"([^\"]*)\"$")
    public void iPostWithTitleAndAuthor(String title, String author) {
        Post newPost = new Post(title, author);
        pr.response = given().
                contentType(ContentType.JSON)
                .body(newPost.toStringJson())
                .when()
                .post(uri);
        ;
    }
}
