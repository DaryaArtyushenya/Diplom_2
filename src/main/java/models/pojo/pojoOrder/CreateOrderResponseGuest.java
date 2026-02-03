package models.pojo.pojoOrder;

public class CreateOrderResponseGuest {
    private Boolean success;
    private String name;
    private OrderGuest order;

    public CreateOrderResponseGuest() {
    }

    public CreateOrderResponseGuest(Boolean success, String name, OrderGuest order) {
        this.success = success;
        this.name = name;
        this.order = order;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderGuest getOrder() {
        return order;
    }

    public void setOrder(OrderGuest order) {
        this.order = order;
    }
}
