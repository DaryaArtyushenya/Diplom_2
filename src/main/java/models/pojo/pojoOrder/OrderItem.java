package models.pojo.pojoOrder;

import java.util.List;

public class OrderItem {

    private String _id;
    private  String updatedAt;
    private String createdAt;
    private String name;
    private String status;
    private List<String> ingredients;
    private Integer number;

    public OrderItem() {
    }

    public OrderItem(String _id, String updatedAt, String createdAt, String name, String status, List<String> ingredients, Integer number) {
        this._id = _id;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.name = name;
        this.status = status;
        this.ingredients = ingredients;
        this.number = number;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
