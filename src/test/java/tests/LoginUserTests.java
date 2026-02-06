package tests;

import api.UserApi;
import factory.UserFactory;
import io.restassured.RestAssured;
import models.data.UserModel;
import models.pojo.pojoUser.UserErrorResponse;
import models.pojo.pojoUser.CreateLoginUserResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
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
    private UserModel userModel;

    @Test
    @DisplayName("Успешный логин")
    void successLoginTest(){
        userModel = UserFactory.correctUser();
        userApi.createUserApi(userModel);
        CreateLoginUserResponse createLoginUserResponse = userApi.loginUserApi(userModel)
                .then()
                .statusCode(200)
                .extract().as(CreateLoginUserResponse.class);
        assertNotNull(createLoginUserResponse.getAccessToken());
        assertNotNull(createLoginUserResponse.getRefreshToken());
        assertNotNull(createLoginUserResponse.getUser());
        assertNotNull(createLoginUserResponse.getSuccess());
    }
    @Test
    @DisplayName("Попытка логина без ввода пароля")
    void loginUserWithoutPasswordTest(){
        userModel = UserFactory.userWithoutPassword();
        UserErrorResponse userErrorResponse = userApi.loginUserApi(userModel)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(UserErrorResponse.class);
        assertFalse(userErrorResponse.getSuccess());
        assertEquals("email or password are incorrect", userErrorResponse.getMessage());
    }
    @Test
    @DisplayName("Попытка логина без ввода имейла")
    void loginUserWithoutEmailTest(){
        userModel = UserFactory.userWithoutEmail();
        UserErrorResponse userErrorResponse = userApi.loginUserApi(userModel)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(UserErrorResponse.class);
        assertFalse(userErrorResponse.getSuccess());
        assertEquals("email or password are incorrect", userErrorResponse.getMessage());
    }
    @AfterEach
    void removeUser(){
        if(userModel!=null){
            userApi.removeUserApi(userModel);
        }
    }
}
