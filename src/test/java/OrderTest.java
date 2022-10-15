import ingredient.Ingredient;
import ingredient.IngredientClient;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.Order;
import order.OrderClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import user.User;
import user.UserClient;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@Epic("Создание заказа, получение списка заказов")
@Feature("Создание заказа, получение списка заказов")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderTest extends CommonTest {
    Integer createdOrderNumber;

    @Test
    @DisplayName("Тест оформления заказа без ингредиентов авторизованным пользователем")
    @Description("При оформлении заказа без ингредиентов возвращается сообщение: Ingredient ids must be provided")
    public void newOrderWithLoginWithoutIngredientsTest() {
        String userAccessToken = userLoginAndGetAccessToken(User.getUserToSuccessLogin());
        Order order = new Order();
        Response orderResponse = OrderClient.sendPostRequestToOrdersWithToken(order, userAccessToken);
        orderResponse.then().assertThat().statusCode(400);
        orderResponse.then().assertThat().body("success", equalTo(false));
        orderResponse.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Тест оформления заказа без ингредиентов неавторизованным пользователем")
    @Description("")
    public void newOrderWithoutLoginWithoutIngredientsTest() {
        Order order = new Order();
        Response orderResponse = OrderClient.sendPostRequestToOrders(order);
        orderResponse.then().assertThat().statusCode(400);
        orderResponse.then().assertThat().body("success", equalTo(false));
        orderResponse.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Тест оформления заказа с неверным хешем ингредиентов")
    @Description("Если в запросе передан невалидный хеш ингредиента, вернётся код ответа 500 Internal Server Error.")
    public void newOrderWithInvalidHashTest() {
        Order order = new Order();
        order.addIngredient(Ingredient.getIngredientWithInvalidHash());
        Response orderResponse = OrderClient.sendPostRequestToOrdersWithToken(order, userLoginAndGetAccessToken(User.getUserToSuccessLogin()));
        orderResponse.then().assertThat().statusCode(500);
        Assert.assertTrue(orderResponse.then().extract().htmlPath().getString("html.body.pre").contains("Internal Server Error"));
    }

    @Test
    @DisplayName("Тест получения заказов конкретного пользователя")
    @Description("При успешном подключении бэкенд вернёт максимум 50 последних заказов пользователя")
    public void getAllOrdersOfUserTest() {
        /*
        Получение заказов конкретного пользователя
        Сервер не возвращает поля owner:
            при сокет-соединении с персональной лентой заказов нужно предоставить серверу авторизационный токен.
        */

        String userAccessToken = userLoginAndGetAccessToken(User.getUserToSuccessLogin());
        Response orderResponse = OrderClient.sendGetRequestToOrdersWithToken(userAccessToken);
        orderResponse.then().assertThat().statusCode(200);
        orderResponse.then().assertThat().body("success", equalTo(true));

        List<Order> ordersList = orderResponse.then().extract().body().path("orders");
        Assert.assertTrue(ordersList.size() <= 50);
    }

    private String userLoginAndGetAccessToken(User userToLogin) {
        Response userResponse = UserClient.sendPostRequestToUserLogin(userToLogin);
        userResponse.then().assertThat().statusCode(200);
        userResponse.then().assertThat().body("success", equalTo(true));
        return userResponse.then().extract().body().path("accessToken");
    }

    @After
    public void tearDown(){
        if (createdOrderNumber != null)
            OrderClient.sendCancelRequestToOrders(createdOrderNumber);
    }

}