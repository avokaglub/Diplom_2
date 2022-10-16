import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import user.User;
import user.UserClient;
import user.UserToken;

import static org.hamcrest.Matchers.equalTo;

@Epic("Регистрация пользователя")
@Feature("Регистрация пользователя")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateUserTest extends CommonTest {
    public static String userAccessToken;

    @Test
    @DisplayName("Тест регистрации пользователя")
    @Description("После успешной регистрации пользователя в ответе возвращается статус success: true")
    public void newUserRegistrationTest() {
        Response response = UserClient.sendPostRequestToUserRegiatration(User.getRandomUser());
        response.then().assertThat().statusCode(200).assertThat().body("success", equalTo(true));
        userAccessToken = response.then().extract().body().path("accessToken").toString().split(" ")[1];
        Assert.assertNotNull(userAccessToken);
    }

    @Test
    @DisplayName("Тест регистрации существующего пользователя")
    @Description("Если пользователь существует, вернётся код ответа 403 Forbidden")
    public void existUserRegistrationTest() {
        UserClient.sendPostRequestToUserRegiatration(User.getUserWitchExist()).then()
                .assertThat().statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Тест регистрации пользователя без пароля (значение = null)")
    @Description("Если нет одного из полей, вернётся код ответа 403 Forbidden")
    public void nullPasswordUserRegistrationTest() {
        UserClient.sendPostRequestToUserRegiatration(User.getUserWithNullPassword()).then()
                .assertThat().statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Тест регистрации пользователя без email (значение = null)")
    @Description("Если нет одного из полей, вернётся код ответа 403 Forbidden")
    public void nullLoginUserRegistrationTest() {
        UserClient.sendPostRequestToUserRegiatration(User.getUserWithNullEmail()).then()
                .assertThat().statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Тест восстановления и сброса пароля пользователя")
    @Description("При восстановлении и сбросе пароля пользователя должно появиться сообщение об отправке email")
    public void passwordResetTest() {
        UserClient.sendPostRequestToPasswordReset(User.getUserWitchExist()).then()
                .assertThat().statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("message", equalTo("Reset email sent"));
    }

    @After
    public void tearDown(){
        if (userAccessToken != null)
            UserClient.sendPostRequestToUserDelete(new UserToken(userAccessToken));
    }
}
