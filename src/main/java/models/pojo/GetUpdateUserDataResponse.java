package models.pojo;

public class GetUpdateUserDataResponse {
    private Boolean success;
    private User user;

    public GetUpdateUserDataResponse() {
    }

    public GetUpdateUserDataResponse(Boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
