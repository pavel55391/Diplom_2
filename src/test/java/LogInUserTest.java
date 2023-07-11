import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.praktikum.User;

import static io.restassured.RestAssured.given;

public class LogInUserTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    public static Object[][] userData() {
        return new Object[][]{
                {"pavelz_16@gmail.com", "123qaz123", true},
                {"pavelz_16123@gmail.com", "123qaz123123", false},
        };
    }

    @ParameterizedTest
    @MethodSource("userData")
    public void LogIn(String email, String password, boolean expected) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(user)
                        .when()
                        .post("/api/auth/login");
        Assertions.assertEquals(expected, response.statusCode() == 200);
    }
}
