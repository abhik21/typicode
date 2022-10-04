package steps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Comment;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NewCommentsStepDefinitions {
    private final prereq pr;
    private String uri;
    private RequestSpecification request;
    private Response response;

    public NewCommentsStepDefinitions(prereq pr) {
        this.pr = pr;
    }

    @Given("^I have an end point comments$")
    public void iHaveAnEndPointComments() {
        request = given();
    }

    @When("^I post a comment \"([^\"]*)\" on post with title \"([^\"]*)\"$")
    public void iPostACommentOnPostWithTitleAndAuthor(String comment, String title) {
        // find postId matching title
        JsonPath jsonPath = RestAssured
                .given()
                .param("q", title)
                .get(pr.url + "/posts")
                .jsonPath();
        List<Integer> post_id = jsonPath.getList("id");

        // Post comments on first post matching title
        uri = String.format("%s/%s/%s/%s", pr.url, "posts", post_id.get(0), "comments");
        Comment newComment = new Comment(comment, post_id.get(0));
        pr.response = request.
                contentType(ContentType.JSON)
                .body(newComment.toStringJson())
                .when()
                .post(uri);
    }

    @And("^response includes my comment \"([^\"]*)\"$")
    public void responseIncludesMyComment(String comment) {
        pr.response.then()
                .statusCode(Integer.parseInt("201"))
                .assertThat()
                .body("body", equalTo(comment));
    }
}