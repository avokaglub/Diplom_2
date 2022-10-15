package ingredient;

import io.restassured.response.ExtractableResponse;

public class Ingredient {
    String _id;
    String name;
    String type;
    Integer proteins;
    Integer fat;
    Integer carbohydrates;
    Integer calories;
    Integer price;
    String image;
    String image_mobile;
    String image_large;
    Integer __v;

    public void init(String _id, String name, String type, Integer proteins, Integer fat, Integer carbohydrates, Integer calories, Integer price, String image, String image_mobile, String image_large, Integer __v) {
        this._id = _id;
        this.name = name;
        this.type = type;
        this.proteins = proteins;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.price = price;
        this.image = image;
        this.image_mobile = image_mobile;
        this.image_large = image_large;
        this.__v = __v;
    }

    public Ingredient init(ExtractableResponse response) {
        this.set_id(response.body().path("data[0]._id").toString());
        this.setName(response.body().path("data[0].name").toString());
        this.setType(response.body().path("data[0].type").toString());
        this.setProteins(Integer.valueOf(response.body().path("data[0].proteins").toString()));
        this.setFat(Integer.valueOf(response.body().path("data[0].fat").toString()));
        this.setCarbohydrates(Integer.valueOf(response.body().path("data[0].carbohydrates").toString()));
        this.setCalories(Integer.valueOf(response.body().path("data[0].calories").toString()));
        this.setPrice(Integer.valueOf(response.body().path("data[0].price").toString()));
        this.setImage(response.body().path("data[0].image").toString());
        this.setImage_mobile(response.body().path("data[0].image_mobile").toString());
        this.setImage_large(response.body().path("data[0].image_large").toString());
        this.set__v(Integer.valueOf(response.body().path("data[0].__v").toString()));
        return this;
    }

    public static Ingredient getIngredientWithInvalidHash() {
        Ingredient newIngredient = new Ingredient();
        newIngredient.init("invalidIngredientHash",
            "Краторная булка N-200i",
            "bun",
            80,
            24,
            53,
            420,
            1255,
            "https://code.s3.yandex.net/react/code/bun-02.png",
            "https://code.s3.yandex.net/react/code/bun-02-mobile.png",
            "https://code.s3.yandex.net/react/code/bun-02-large.png",
            0
        );
        return newIngredient;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getProteins() {
        return proteins;
    }

    public void setProteins(Integer proteins) {
        this.proteins = proteins;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getCarbohydrates(String s) {
        return carbohydrates;
    }

    public void setCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_mobile() {
        return image_mobile;
    }

    public void setImage_mobile(String image_mobile) {
        this.image_mobile = image_mobile;
    }

    public String getImage_large() {
        return image_large;
    }

    public void setImage_large(String image_large) {
        this.image_large = image_large;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }
}
