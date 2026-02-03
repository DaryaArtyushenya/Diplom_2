package models.pojo.pojoOrder;

public class CreateOrderAuthorizeUserResponse {
    private Boolean success;
    private String name;
    private OrderAuthorizeUser order;

    public CreateOrderAuthorizeUserResponse() {
    }

    public CreateOrderAuthorizeUserResponse(Boolean success, String name, OrderAuthorizeUser order) {
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

    public OrderAuthorizeUser getOrder() {
        return order;
    }

    public void setOrder(OrderAuthorizeUser order) {
        this.order = order;
    }
}
