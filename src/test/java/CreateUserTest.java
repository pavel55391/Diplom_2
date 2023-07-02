import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.User;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class CreateUserTest {

    private static User user;

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        Random random = new Random();
        String name = "mihail" + random.nextInt();
        String email = "mihail" + random.nextInt() + "@gmail.com";
        String password = "mihail" + random.nextInt() + "@gmail.com";
        user = new User(name, email, password);
    }

    @Test
    @DisplayName("Check create user")
    public void createUser(){

        Response response = 
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(user)
                        .when()
                        .post("/api/auth/register");
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check logIn user")
    public void logInUser(){
        String json = "{ \"email\": \"pavelz_16@gmail.com\",\n" +
                "\"password\": \"123qaz123\"}";

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/login");
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check logIn user without required field")
    public void logInWithoutRequiredField(){
        String json = "{\"email\": \n" +
                "\"password\": \n" + "}";

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/register");
        response.then().assertThat().statusCode(400);
    }
}

