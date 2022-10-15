package order;

import ingredient.Ingredient;
import io.restassured.response.ExtractableResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order {
    private String _id;
    private ArrayList<Ingredient> ingredients;
    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private Integer number;

    public Order(){
        ingredients = new ArrayList<>();
    }

    public Order init(ExtractableResponse response) {
        response.path("orders[0]");
        this.set_id(response.body().path("data[0]._id").toString());
        this.setStatus(response.body().path("data[0].status").toString());
        this.setName(response.body().path("data[0].name").toString());
        this.setCreatedAt(response.body().path("data[0].createdAt").toString());
        this.setUpdatedAt(response.body().path("data[0].updatedAt").toString());
        this.setNumber(Integer.valueOf(response.body().path("data[0].number").toString()));
        return this;
    }

    @Override
    public String toString() {
        String orderInfoPattern = "[Order info]: Ingredients: %s;";
        String orderInfo = "";
        if (!ingredients.isEmpty())
            String.format(orderInfoPattern, ingredients.toString());
        return orderInfo;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}