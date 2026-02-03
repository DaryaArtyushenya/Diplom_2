package models.pojo.pojoOrder;

import java.util.List;

public class OrderAuthorizeUser extends OrderGuest {
    private String price;
    private  String updatedAt;
    private String createdAt;
    private String name;
    private String status;
    private List<Owner> owners;
    private List<Ingredient> ingredients;

    public OrderAuthorizeUser() {
    }

    public OrderAuthorizeUser(Integer number, String price, String updatedAt, String createdAt, String name, String status, List<Owner> owners, List<Ingredient> ingredients) {
        super(number);
        this.price = price;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.name = name;
        this.status = status;
        this.owners = owners;
        this.ingredients = ingredients;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updateAt) {
        this.updatedAt = updateAt;
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

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
