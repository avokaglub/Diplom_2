package ingredient;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import user.BaseClient;

public class IngredientClient extends BaseClient {
    @Step("Send POST request to /api/ingredients")
    public static Response sendPostRequestToIngredients(){
        return getSpec().get("/api/ingredients");
    }
}
