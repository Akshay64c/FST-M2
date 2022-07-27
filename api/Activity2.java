package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {

    final String baseURI = "https://petstore.swagger.io/v2/user";

    @Test(priority = 1)
    public void addUser() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/java/activities/pet.json");

        String reqBody = new String(fis.readAllBytes());

        Response response = given().contentType(ContentType.JSON)
                .body(reqBody).when().post(baseURI);

        fis.close();
        System.out.println(response.getBody().asString());
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("9010"));     }

    @Test(priority = 2)
    public void getUser() throws FileNotFoundException {
        File fout = new File("src/test/java/activities/userGet.json");

        Response response = given().contentType(ContentType.JSON)
                .pathParam("username", "akshay64")
                .when().get(baseURI+"/{username}");

        String resBody = response.getBody().asPrettyString();

        try{
            fout.createNewFile();
            FileWriter writer = new FileWriter(fout.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        response.then().body("id", equalTo(9010));
        response.then().body("username", equalTo("akshay64"));
        response.then().body("firstName", equalTo("Akshay"));
        response.then().body("lastName", equalTo("Sharma"));
        response.then().body("email", equalTo("akshaysharma@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763456"));
    }

    @Test(priority = 3)
    public void deleteUser(){
        Response response =
                given().contentType(ContentType.JSON)
                        .pathParam("username", "akshay64")
                        .when().delete(baseURI + "/{username}");
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("akshay64"));
    }
}
