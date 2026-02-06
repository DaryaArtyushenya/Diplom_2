package api;

import io.qameta.allure.Step;
import models.data.UserModel;
import io.restassured.response.Response;
import models.pojo.pojoUser.UpdateUserRequest;

import static io.restassured.RestAssured.given;

public class UserApi {
    @Step("Создание  пользователя")
    public Response createUserApi(UserModel userModel){
        return given()
                .header("Content-type","application/json")
                .body(userModel)
                .post("/api/auth/register");
    }
    @Step("Авторизация пользователя")
    public Response loginUserApi(UserModel userModel){
        return given()
                .header("Content-type","application/json")
                .body(userModel)
                .post("/api/auth/login");
    }
    @Step("Удаление пользователя")
    public void removeUserApi(UserModel userModel){
        Response loginResponse = loginUserApi(userModel);
        String token = loginResponse.jsonPath().getString("accessToken");
        given()
                .header("Authorization", token)
                .header("Content-type","application/json")
                .delete("/api/auth/userModel");
    }

    @Step("Обновление данных пользователя")
    public Response changeUserDataApi(String token, UpdateUserRequest updateUserRequest){
        return given()
                .header("Authorization", token)
                .header("Content-type","application/json")
                .body(updateUserRequest)
                .patch("/api/auth/user");

    }
}

