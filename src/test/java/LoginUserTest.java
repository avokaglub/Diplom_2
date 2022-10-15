import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import user.User;
import user.UserToken;
import user.UserClient;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Авторизация пользователя")
@Feature("Авторизация пользователя")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginUserTest extends CommonTest {
    public static UserToken userRefreshToken;

    @Test
    @DisplayName("Тест успешной авторизации пользователя")
    @Description("При успешной авторизации пользователя в теле ответа присутствуют токены")
    public void userAuthTest() {
        Response userResponse = UserClient.sendPostRequestToUserLogin(User.getUserToSuccessLogin());
        userResponse.then()
                .assertThat().statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("accessToken", notNullValue())
                .assertThat().body("refreshToken", notNullValue());
        userRefreshToken = new UserToken(userResponse.then().extract().body().path("refreshToken"));
    }

    @Test
    @DisplayName("Тест авторизации пользователя с пустым логином")
    @Description("Если логин или пароль неверные или нет одного из полей, вернётся код ответа 401 Unauthorized")
    public void userWithEmptyLoginAuthTest() {
        UserClient.sendPostRequestToUserLogin(User.getUserWithEmptyLogin()).then()
                .assertThat().statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Тест авторизации пользователя с пустым паролем")
    @Description("Если логин или пароль неверные или нет одного из полей, вернётся код ответа 401 Unauthorized")
    public void userWithEmptyPasswordAuthTest() {
        UserClient.sendPostRequestToUserLogin(User.getUserWithEmptyPassword()).then()
                .assertThat().statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Тест выхода из системы")
    @Description("После успешного выхода из системы в ответе сервера возвращается статус success: true и сообщение 'Successful logout'")
    public void userLogoutTest() {
        UserClient.sendPostRequestToUserLogout(userRefreshToken).then()
                .assertThat().statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("message", equalTo("Successful logout"));
    }
}
