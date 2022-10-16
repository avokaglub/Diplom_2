import ingredient.Ingredient;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.Order;
import order.OrderClient;
import order.OrdersList;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import user.User;
import user.UserClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String userAccessToken = userLoginAndGetAccessToken(User.getUserWitchExist());
        Order order = new Order();
        Response orderResponse = OrderClient.sendPostRequestToOrdersWithToken(order, userAccessToken);
        orderResponse.then().assertThat().statusCode(400);
        orderResponse.then().assertThat().body("success", equalTo(false));
        orderResponse.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Тест оформления заказа без ингредиентов неавторизованным пользователем")
    @Description("При оформлении заказа без ингредиентов неавторизованным пользователем возвращается сообщение: Ingredient ids must be provided")
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
        Response orderResponse = OrderClient.sendPostRequestToOrdersWithToken(order, userLoginAndGetAccessToken(User.getUserWitchExist()));
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

        String userAccessToken = userLoginAndGetAccessToken(User.getUserWitchExist());
        Response orderResponse = OrderClient.sendGetRequestToOrdersWithToken(userAccessToken);
        orderResponse.then().assertThat().statusCode(200);
        orderResponse.then().assertThat().body("success", equalTo(true));

        List<Order> ordersList = orderResponse.then().extract().body().path("orders");
        Assert.assertTrue(ordersList.size() <= 50);
    }

    @Test
    @DisplayName("Тест получения заказов")
    @Description("При подключении бэкенд приложения вернёт максимум 50 последних заказов. Они сортируются по времени обновления.")
    public void getAllOrdersListTest() {
        String userAccessToken = userLoginAndGetAccessToken(User.getUserWitchExist());
        Response getAllOrdersResponse = OrderClient.sendGetRequestToOrdersAll(userAccessToken);
        getAllOrdersResponse.then().assertThat().statusCode(200);
        getAllOrdersResponse.then().assertThat().body("success", equalTo(true));
        Assert.assertTrue((Integer) getAllOrdersResponse.then().extract().body().path("total") > 0);
        Assert.assertTrue((Integer) getAllOrdersResponse.then().extract().body().path("totalToday") >= 0);

        OrdersList returnedOrders = getAllOrdersResponse.getBody().as(OrdersList.class);
        ArrayList<Order> orders = returnedOrders.getOrders();
        ArrayList<Order> ordersOrdered = (ArrayList<Order>) orders.clone();
        ordersOrdered.sort(ORDER_COMPARATOR);
        Assert.assertTrue(ordersOrdered.equals(orders));
    }

    @After
    public void tearDown(){
        if (createdOrderNumber != null)
            OrderClient.sendCancelRequestToOrders(createdOrderNumber);
    }

    private String userLoginAndGetAccessToken(User userToLogin) {
        Response userResponse = UserClient.sendPostRequestToUserLogin(userToLogin);
        userResponse.then().assertThat().statusCode(200);
        userResponse.then().assertThat().body("success", equalTo(true));
        return userResponse.then().extract().body().path("accessToken");
    }

    private static Comparator<Order> ORDER_COMPARATOR = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Date date1 = formatter.parse(o1.getUpdatedAt());
                Date date2 = formatter.parse(o2.getUpdatedAt());

                return date2.compareTo(date1);
            } catch (ParseException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
    };
}