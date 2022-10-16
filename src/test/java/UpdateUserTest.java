import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import user.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Обновление информации о пользователе")
@Feature("Обновление информации о пользователе")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateUserTest extends CommonTest {
        public static String userAccessToken;
        public static String userRefreshToken;

        @Test
        @DisplayName("Обновление информации о пользователе после успешной авторизации")
        @Description("Обновление информации о пользователе после успешной авторизации")
        public void userUpdateWithLoginTest() {
                Response userLoginResponse = UserClient.sendPostRequestToUserLogin(User.getUserToSuccessLogin());
                System.out.print(userLoginResponse.prettyPrint());
                userLoginResponse.then()
                        .assertThat().statusCode(200)
                        .assertThat().body("success", equalTo(true))
                        .assertThat().body("accessToken", notNullValue())
                        .assertThat().body("refreshToken", notNullValue());
                userAccessToken = userLoginResponse.then().extract().body().path("accessToken");
                userRefreshToken = userLoginResponse.then().extract().body().path("refreshToken");

                User userInfo = User.getRandomUser();
                Response userResponse = UserClient.sendPatchRequestToUserAuth(new UserToken(userAccessToken), userInfo);
                System.out.print(userResponse.prettyPrint());
                userResponse.then().assertThat().statusCode(200);
                userResponse.then().assertThat().body("success", equalTo(true));
                userResponse.then().assertThat().body("user.email", equalTo(userInfo.getEmail()));
                userResponse.then().assertThat().body("user.name", equalTo(userInfo.getName()));
        }

        @Test
        @DisplayName("Обновление информации о пользователе без авторизации")
        @Description("Обновление информации о пользователе без авторизации")
        public void userUpdateWithoutLoginTest() {
                User userInfo = User.getRandomUser();
                Response userResponse = UserClient.sendPatchRequestToUserAuth(userInfo);
                System.out.print(userResponse.prettyPrint());
                userResponse.then().assertThat().statusCode(401);
                userResponse.then().assertThat().body("success", equalTo(false));
                userResponse.then().assertThat().body("message", equalTo("You should be authorised"));
        }

}
