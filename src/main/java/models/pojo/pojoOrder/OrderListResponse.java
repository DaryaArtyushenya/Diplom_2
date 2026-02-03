package models.pojo.pojoOrder;

import java.util.List;

public class OrderListResponse {
    private Boolean success;
    private List<OrderItem> orders;
    private Integer total;
    private Integer totalToday;

    public OrderListResponse() {
    }

    public OrderListResponse(Boolean success, List<OrderItem> orders, Integer total, Integer totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(Integer totalToday) {
        this.totalToday = totalToday;
    }
}
