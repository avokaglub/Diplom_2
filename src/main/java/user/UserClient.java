package user;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserClient extends BaseClient {
    @Step("Send POST request to /api/auth/register")
    public static Response sendPostRequestToUserRegiatration(User user){
        return getSpec().and().body(user).when().post("/api/auth/register");
    }

    @Step("Send POST request to /api/auth/login")
    public static Response sendPostRequestToUserLogin(User user){
        return getSpec().and().body(user).when().post("/api/auth/login");
    }

    @Step("Send POST request to /api/auth/logout")
    public static Response sendPostRequestToUserLogout(UserToken userToken){
        return getSpec().and().body(userToken).when().post("/api/auth/logout");
    }

    @Step("Send PATCH request to /api/auth/user ")
    public static Response sendPatchRequestToUserAuth(UserToken token, User user){
        return getSpec().and().header("Authorization", token.token).and().body(user).when().patch("/api/auth/user ");
    }

    @Step("Send PATCH request to /api/auth/user ") // редактирование информации о пользователе
    public static Response sendPatchRequestToUserAuth(User user){
        return getSpec().and().body(user).when().patch("/api/auth/user ");
    }

    @Step("Send POST request to /api/password-reset")
    public static Response sendPostRequestToPasswordReset(User user){
        return getSpec().and().body(user).when().post("/api/password-reset");
    }
    //DELETE https://stellarburgers.nomoreparties.site/api/auth/user
    @Step("Send DELETE request to /api/auth/user")
    public static Response sendPostRequestToUserDelete(UserToken token/*, User user*/){
        return getSpec().and().header("Authorization", token.token).and()/*.body(user)*/.when().delete("/api/auth/user");
    }
}
