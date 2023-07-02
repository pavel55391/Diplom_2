import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.LoginResponseBody;
import ru.yandex.praktikum.OrderResponse;

import static io.restassured.RestAssured.given;

public class GetOrderFromUserTest {

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Get user order without authorization")
    public void getUserOrderWithoutAuthorization() {
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

        Response orderResponse =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(responseBody.getPureAccessToken())
                        .and()
                        .get("/api/orders");
        OrderResponse order = gson.fromJson(orderResponse.getBody().print(), OrderResponse.class);
        Assertions.assertTrue(order.isSuccess());
    }

    @Test
    @DisplayName("Get user order with authorization")
    public void getUserOrderWithAuthorization() {

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .get("/api/orders");
        response.then().assertThat().statusCode(401);
    }
}
