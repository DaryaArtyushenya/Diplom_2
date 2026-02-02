package tests;

import api.UserApi;
import factory.UserFactory;
import io.restassured.RestAssured;
import models.data.UserModel;
import models.pojo.UserErrorResponse;
import models.pojo.CreateLoginUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginUserTests {
    @BeforeEach
    void setUp(){
        RestAssured.baseURI= "https://stellarburgers.education-services.ru/";
    }
    UserApi userApi = new UserApi();
    @Test
    @DisplayName("Успешный логин")
    void successLoginTest(){
        UserModel userModel = UserFactory.correctUser();
        userApi.createUserApi(userModel);
        CreateLoginUserResponse createLoginUserResponse = userApi.loginUserApi(userModel)
                .then()
                .statusCode(200)
                .extract().as(CreateLoginUserResponse.class);
        assertNotNull(createLoginUserResponse.getAccessToken());
        assertNotNull(createLoginUserResponse.getRefreshToken());
        assertNotNull(createLoginUserResponse.getUser());
        assertNotNull(createLoginUserResponse.getSuccess());
        userApi.removeUserApi(userModel);
    }
    @Test
    @DisplayName("Попытка логина без ввода пароля")
    void loginUserWithoutPasswordTest(){
        UserModel userModel = UserFactory.userWithoutPassword();
        UserErrorResponse userErrorResponse = userApi.loginUserApi(userModel)
                .then()
                .statusCode(401)
                .extract().as(UserErrorResponse.class);
        assertFalse(userErrorResponse.getSuccess());
        assertEquals("email or password are incorrect", userErrorResponse.getMessage());
    }
    @Test
    @DisplayName("Попытка логина без ввода имейла")
    void loginUserWithoutEmailTest(){
        UserModel userModel = UserFactory.userWithoutEmail();
        UserErrorResponse userErrorResponse = userApi.loginUserApi(userModel)
                .then()
                .statusCode(401)
                .extract().as(UserErrorResponse.class);
        assertFalse(userErrorResponse.getSuccess());
        assertEquals("email or password are incorrect", userErrorResponse.getMessage());
    }
}
