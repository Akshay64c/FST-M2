package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3 {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("status",equalTo("alive"))
                .build();
    }
    @DataProvider
    public Object[][] petProvider(){
        Object[][] data = new Object[][]{
                { 88234, "Snow", "alive" },
                { 88235, "Rain", "alive" }
        };
        return  data;
    }

    @Test(dataProvider = "petProvider",priority = 1)
    public void addpet(int id, String name, String status){

        String reqBody = "{\"id\": "+id+", \"name\": \""+name+"\", \"status\": \""+status+"\"}";
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();

        System.out.println(response.getBody().asString());
        response.then().spec(responseSpec);
}

    @Test(dataProvider = "petProvider",priority = 2)
    public void getPet(int id, String name, String status){
        Response response = given().spec(requestSpec)
                .pathParam("petId",id)
                .when().get("/{petId}");

        System.out.println(response.asPrettyString());
        response.then().spec(responseSpec).body("name",equalTo(name));

    }

    @Test(dataProvider = "petProvider",priority = 3)
    public void removePet(int id, String name,String status){
        Response response = given().spec(requestSpec)
                .pathParam("petId",id)
                .when().delete("/{petId}");

        System.out.println(response.getBody().asString());
        response.then().body("code",equalTo(200));

    }

}
