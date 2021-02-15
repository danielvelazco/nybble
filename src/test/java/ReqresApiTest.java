import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ReqresApiTest {

    public static RequestSpecification reqSpec() {
        RestAssured.baseURI = "https://reqres.in";
        RequestSpecification httpRequest = given();
        return httpRequest;
    }

    @Test // GET request
    public void GetSingleUser()
    {
        Response response = reqSpec().request(Method.GET, "/api/users/2");
        int statusCode = response.getStatusCode();
        // Assert status code of the request
        Assert.assertEquals(statusCode, 200, "Wrong status code");

        // Assert part of the body
        String expectedFirstName = "Janet";
        String expectedUrl = "https://reqres.in/#support-heading";
        JsonPath jsonResponse = response.jsonPath();
        String actualFirstName = jsonResponse.get("data.first_name");
        String actualUrl = jsonResponse.get("support.url");
        Assert.assertEquals(actualFirstName, expectedFirstName, "First Name doesn't match");
        Assert.assertEquals(actualUrl, expectedUrl, "Url doesn't match");

        // BDD style using path param
        String url = "https://reqres.in/api/users/";
        String user1 = "2";
        given().
                pathParam("user",user1).
        when().
                get(url+"{user}").
        then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void GetSingleUserNotFound() {
        String url = "https://reqres.in/api/users/";
        String user2 = "23";
        given().
                pathParam("user",user2).
        when().
                get(url+"{user}").
        then().
                assertThat().
                statusCode(404);
    }

    @Test // POST request sending body
    public void CreateUser() {
        {
            RequestSpecification request = reqSpec();

            JSONObject requestParams = new JSONObject();
            requestParams.put("name", "daniel");
            requestParams.put("job", "tester");

            Response response = request.body(requestParams.toJSONString()).post("/api/users");
            int statusCode = response.getStatusCode();
            // Assert status code of the request
            Assert.assertEquals(statusCode, 201);

            ResponseBody body = response.getBody();
            System.out.println("Response Body is: " + body.asString());

            // NOTE: Body assertion is missing here. The issue is that the endpoint is not returning what I'm sending to be asserted

        }
    }

    @Test // PUT request using path param and sending body - BDD style
    public void UpdateUser() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "daniel");
        requestParams.put("job", "tester lead");
        String url = "https://reqres.in/api/users/";
        String user1 = "2";
        given().
                pathParam("user",user1).
                body(requestParams.toJSONString()).
        when().
                get(url+"{user}").
        then().
                assertThat().
                statusCode(200);

        // NOTE: Body assertion is missing here. The issue is that the endpoint is not returning what I'm sending to be asserted

    }

    @Test // DELETE request
    public void DeleteUser() {
        Response response = reqSpec().request(Method.DELETE, "/api/users/2");
        int statusCode = response.getStatusCode();
        //Assert status code of the request
        Assert.assertEquals(statusCode, 204, "Wrong status code");
    }

}
