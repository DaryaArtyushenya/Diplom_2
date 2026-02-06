package models.pojo.pojoOrder;

public class OrderResponseError {
    private Boolean success;
    private String message;

    public OrderResponseError() {
    }

    public OrderResponseError(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
