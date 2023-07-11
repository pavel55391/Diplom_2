import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.LoginResponseBody;
import ru.yandex.praktikum.Order;
import ru.yandex.praktikum.UserData;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.praktikum.IngredientIds.*;

public class CreateUserOrderTest {
    private final Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Create order with ingredients and authorization")
    public void createOrderWithAuthorization() {
        UserData data = new UserData("pavelz_16@gmail.com" , "123qaz123");

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/auth/login");

        LoginResponseBody responseBody = gson.fromJson(response.getBody().print(), LoginResponseBody.class);
        List<String> ingredients = List.of(INGREDIENT_ID_1, INGREDIENT_ID_2);
        Order order = new Order();
        order.setIngredients(ingredients);
        String ingredientsJson = gson.toJson(order);

                Response orderResponse =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(responseBody.getPureAccessToken())
                        .and()
                        .body(ingredientsJson)
                        .and()
                        .post("/api/orders");
        orderResponse.then().assertThat().statusCode(200).and().body("success" , notNullValue());
    }

    @Test
    @DisplayName("Create order with ingredients and without authorization")
    public void orderWithIngredientsAndWithoutAuthorization() {
        List<String> ingredients = List.of(INGREDIENT_ID_1, INGREDIENT_ID_2);
        Order order = new Order();
        order.setIngredients(ingredients);
        String ingredientsJson = gson.toJson(order);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(ingredientsJson)
                        .when()
                        .post("/api/orders");
        response.then().assertThat().statusCode(200).and().body("name" , notNullValue());
    }

    @Test
    @DisplayName("Create order with ingredients")
    public void orderWithIngredients(){
        List<String> ingredients = List.of(INGREDIENT_ID_1, INGREDIENT_ID_2);
        Order order = new Order();
        order.setIngredients(ingredients);
        String ingredientsJson = gson.toJson(order);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(ingredientsJson)
                        .when()
                        .post("/api/orders");
        response.then().assertThat().statusCode(200).and().body("name" , notNullValue());
    }

    @Test
    @DisplayName("Create order without with ingredients")
    public void orderWithoutIngredients() {
        List<String> ingredients = List.of(NOT_AN_INGREDIENT_ID);
        Order order = new Order();
        order.setIngredients(ingredients);
        String ingredientsJson = gson.toJson(order);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(ingredientsJson)
                        .when()
                        .post("/api/orders");
        response.then().assertThat().statusCode(500);
    }

    @Test
    @DisplayName("Create order with invalid hash ingredients")
    public void orderWithInvalidHashIngredients() {
        List<String> ingredients = List.of(INGREDIENT_ID_1);
        String ingredientsJson = gson.toJson(ingredients);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(ingredientsJson)
                        .when()
                        .post("/api/orders");
        response.then().assertThat().statusCode(400);
    }
}
