import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.LoginResponseBody;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserOrderTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Create order with ingredients and authorization")
    public void createOrderWithAuthorization() {
        String json = "{ \"email\": \"pavelz_16@gmail.com\",\n" +
                "\"password\": \"123qaz123\"}";

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/login");

        Gson gson = new Gson();
        LoginResponseBody responseBody = gson.fromJson(response.getBody().print(), LoginResponseBody.class);

        String jsonOrder = "{\n" + "\"ingredients\": [\"61c0c5a71d1f82001bdaaa70\", \"61c0c5a71d1f82001bdaaa6d\"]\n" + "}";
        Response orderResponse =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(responseBody.getPureAccessToken())
                        .and()
                        .body(jsonOrder)
                        .and()
                        .post("/api/orders");
        orderResponse.then().assertThat().statusCode(200).and().body("success" , notNullValue());
    }

    @Test
    @DisplayName("Create order with ingredients and without authorization")
    public void orderWithoutIngredientsAndAuthorization() {
        String json = "{\n" + "\"ingredients\": [\"61c0c5a71d1f82001bdaaa70\", \"61c0c5a71d1f82001bdaaa6d\"]\n" + "}";

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/orders");
        response.then().assertThat().statusCode(200).and().body("name" , notNullValue());
    }

    @Test
    @DisplayName("Create order with ingredients")
    public void orderWithIngredients(){
        String json = "{\n" + "\"ingredients\": [\"61c0c5a71d1f82001bdaaa70\", \"61c0c5a71d1f82001bdaaa6d\"]\n" + "}";

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/orders");
        response.then().assertThat().statusCode(200).and().body("name" , notNullValue());
    }

    @Test
    @DisplayName("Create order without with ingredients")
    public void orderWithoutIngredients() {
        String json = "{\n" + "\"ingredients\": [\"null\"]\n" + "}";

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/orders");
        response.then().assertThat().statusCode(500);
    }

    @Test
    @DisplayName("Create order with invalid hash ingredients")
    public void orderWithInvalidHashIngredients() {
        String json = "{\n" + "\"ingredients\": [\"60d3b41abdacab0026a733c6\"]\n" + "}";

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/orders");
        response.then().assertThat().statusCode(400);
    }
}
