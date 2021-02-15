import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ReqresApiTest {

    @Test // GET request
    public void GetSingleUser()
    {
        // Regular style
        // Base endpoint
        RestAssured.baseURI = "https://reqres.in";
        // Request specifications
        RequestSpecification httpRequest = given();
        // Request and response
        Response response = httpRequest.request(Method.GET, "/api/users/2");
        int statusCode = response.getStatusCode();
        //Assert status code of the request
        Assert.assertEquals(statusCode, 200, "Wrong status code");

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
            RestAssured.baseURI = "https://reqres.in";
            RequestSpecification request = RestAssured.given();

            JSONObject requestParams = new JSONObject();
            requestParams.put("name", "daniel");
            requestParams.put("job", "tester");
            request.body(requestParams.toJSONString());
            Response response = request.post("/api/users");
            System.out.println(response.asString());
            int statusCode = response.getStatusCode();
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
        RestAssured.baseURI = "https://reqres.in";
        // Request specifications
        RequestSpecification httpRequest = given();
        // Request and response
        Response response = httpRequest.request(Method.DELETE, "/api/users/2");
        int statusCode = response.getStatusCode();
        //Assert status code of the request
        Assert.assertEquals(statusCode, 204, "Wrong status code");
    }

}
