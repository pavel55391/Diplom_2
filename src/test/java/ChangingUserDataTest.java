import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.LoginResponseBody;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ChangingUserDataTest {

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Changing user data with authorization")
    public void changeUserDataWithAuthorization(){
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


        String changedJson = "{ \"email\": \"pavelz_16@gmail.com\",\n" +
                "\"password\": \"123qaz123\", \"name\": \"Pavelllll\"}";
        Response userResponse =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(responseBody.getPureAccessToken())
                        .and()
                        .body(changedJson)
                        .when()
                        .patch("/api/auth/user");
        userResponse.then().assertThat().statusCode(200).body("success" , equalTo(true));
    }

    @Test
    @DisplayName("Changing user data without authorization")
    public void changingUserDataWithoutAuthorization(){

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .patch("/api/auth/user");
        response.then().statusCode(401).body("success" , equalTo(false));
    }
}
