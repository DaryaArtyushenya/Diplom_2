package models.pojo.pojoOrder;

import java.util.List;

public class CreateOrderRequest {
    List<String> ingredients;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
