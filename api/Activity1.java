package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {

    final String baseURI = "https://petstore.swagger.io/v2/pet";

    @Test(priority = 1)
    public void addPet(){
        String reqBody = "{\"id\": 88990,\"name\": \"Ripley\",\"status\": \"dead\"}";

        Response response = given().contentType(ContentType.JSON)
                .body(reqBody).when().post(baseURI);

        response.then().body("id",equalTo(88990));
        response.then().body("name",equalTo("Ripley"));
        response.then().body("status",equalTo("dead"));
    }

    @Test(priority = 2)
    public void getPet(){
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("petId",88990)
                .get(baseURI+"/{petId}");

        response.then().body("id",equalTo(88990));
        response.then().body("name",equalTo("Ripley"));
        response.then().body("status",equalTo("dead"));
    }

    @Test(priority = 3)
    public void deletePet(){
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("petId",88990)
                .delete(baseURI+"/{petId}");

        response.then().body("code",equalTo(200));
        response.then().body("message",equalTo("88990"));
    }
}
