package tests;

import api.UserApi;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import factory.UserFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.data.UserModel;
import models.pojo.GetUpdateUserDataResponse;
import models.pojo.UpdateUserRequest;
import models.pojo.UserErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ChangeUserDataTests {
    @BeforeEach
    void setUp(){
        RestAssured.baseURI= "https://stellarburgers.education-services.ru/";
    }
    UserApi userApi = new UserApi();
    Faker faker = new Faker();
    @Test
    @DisplayName("Проверка обновления имейла пользователя")
    void successUpdateUserEmailTest(){
        UserModel userModel = UserFactory.correctUser();
        userApi.createUserApi(userModel);
        Response response = userApi.loginUserApi(userModel);
        String token = response.jsonPath().getString("accessToken");
        String newEmail = faker.internet().emailAddress();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail(newEmail);
        System.out.println(new Gson().toJson(updateUserRequest));
        GetUpdateUserDataResponse getUpdateUserDataResponse = userApi.changeUserDataApi(token,updateUserRequest)
                .then().log().all()
                .statusCode(200)
                .extract().as(GetUpdateUserDataResponse.class);
        assertEquals(newEmail, getUpdateUserDataResponse.getUser().getEmail());
    }
    @Test
    @DisplayName("Проверка обновления имени пользователя")
    void successUpdateUserNameTest(){
        UserModel userModel = UserFactory.correctUser();
        userApi.createUserApi(userModel);
        Response response = userApi.loginUserApi(userModel);
        String token = response.jsonPath().getString("accessToken");
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        String newName = faker.name().firstName();
        updateUserRequest.setName(newName);
        GetUpdateUserDataResponse getUpdateUserDataResponse = userApi.changeUserDataApi(token, updateUserRequest)
                .then()
                .statusCode(200)
                .extract().as(GetUpdateUserDataResponse.class);
        assertEquals(newName, getUpdateUserDataResponse.getUser().getName());
    }
    @Test
    @DisplayName("Попытка обновить не существующего пользователя")
    void successUpdateUserDataTest(){
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        String newName = faker.name().firstName();
        updateUserRequest.setName(newName);
        UserErrorResponse userErrorResponse = userApi.changeUserDataApi("", updateUserRequest)
                .then()
                .statusCode(401)
                .extract().as(UserErrorResponse.class);
        assertEquals("You should be authorised", userErrorResponse.getMessage());
        assertFalse(userErrorResponse.getSuccess());
    }
    @Test
    @DisplayName("Проверка обновления имейла пользователя на уже существующий")
    void userAlreadyExistUpdateTest(){
        UserModel userModel = UserFactory.correctUser();
        UserModel userModel1 = UserFactory.correctUser();
        userApi.createUserApi(userModel);
        userApi.createUserApi(userModel1);
        Response response = userApi.loginUserApi(userModel);
        String token = response.jsonPath().getString("accessToken");
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        String newEmail = userModel1.getEmail();
        updateUserRequest.setEmail(newEmail);
        System.out.println(new Gson().toJson(updateUserRequest));
        UserErrorResponse userErrorResponse = userApi.changeUserDataApi(token,updateUserRequest)
                .then().log().all()
                .statusCode(403)
                .extract().as(UserErrorResponse.class);
        assertEquals("User with such email already exists", userErrorResponse.getMessage());
        userApi.removeUserApi(userModel);
    }


}
