package order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import user.BaseClient;

public class OrderClient extends BaseClient {
    @Step("Send POST request to /api/orders")
    public static Response sendPostRequestToOrders(Order order){
        return getSpec().and().body(order).when().post("/api/orders");
    }

    @Step("Send POST request to /api/orders")
    public static Response sendPostRequestToOrdersWithToken(Order order, String token){
        return getSpec().header("Authorization", token).and().body(order).when().post("/api/orders");
    }

    @Step("Send GET request to /api/orders/all")
    public static Response sendGetRequestToOrdersAll(){
        return getSpec().get("/api/orders/all");
    }

    @Step("Send GET request to /api/orders")
    public static Response sendGetRequestToOrdersWithToken(String token){
        return getSpec().header("Authorization", token).get("/api/orders");
    }

    @Step("Send PUT request to /api/orders/cancel")
    public static Response sendCancelRequestToOrders(Integer orderTrack){
        return getSpec().and().body(orderTrack.toString()).when().put("/api/orders/cancel");
    }
}
