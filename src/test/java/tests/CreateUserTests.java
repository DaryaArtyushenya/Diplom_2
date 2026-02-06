package tests;

import factory.UserFactory;
import api.UserApi;
import io.restassured.RestAssured;
import models.data.UserModel;
import models.pojo.pojoUser.CreateLoginUserResponse;
import models.pojo.pojoUser.UserErrorResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTests {
    @BeforeEach
    void setUp(){
        RestAssured.baseURI="https://stellarburgers.education-services.ru/";
    }
    UserApi userApi = new UserApi();
    private UserModel userModel;

    @Test
    @DisplayName("Пользователь успешно создан со всеми обязательными полями")
    void successUserCreationTest(){
        userModel = UserFactory.correctUser();
        CreateLoginUserResponse createLoginUserResponse = userApi.createUserApi(userModel)
             .then()
             .statusCode(200)
                .extract().as(CreateLoginUserResponse.class);
        assertNotNull(createLoginUserResponse.getAccessToken());
        assertNotNull(createLoginUserResponse.getRefreshToken());
        assertNotNull(createLoginUserResponse.getUser());
        assertNotNull(createLoginUserResponse.getSuccess());
}
@Test
@DisplayName("Такой пользователь уже существует")
    void userAlreadyExistTest(){
    userModel = UserFactory.correctUser();
    userApi.createUserApi(userModel);
    UserErrorResponse userErrorResponse = userApi.createUserApi(userModel)
            .then()
            .statusCode(HttpStatus.SC_FORBIDDEN)
            .extract().as(UserErrorResponse.class);
    assertFalse(userErrorResponse.getSuccess());
    assertEquals("User already exists", userErrorResponse.getMessage());
}
@Test
@DisplayName("Попытка создания пользователя без ввода имейла")
    void userCreateErrorWithoutEmailTest(){
        userModel = UserFactory.userWithoutEmail();
        UserErrorResponse createUserErrorResponse = userApi.createUserApi(userModel)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract().as(UserErrorResponse.class);
         assertFalse(createUserErrorResponse.getSuccess());
         assertEquals("Email, password and name are required fields", createUserErrorResponse.getMessage());
}
    @Test
    @DisplayName("Попытка создания пользователя без ввода пароля")
    void userCreateErrorWithoutPasswordTest(){
        userModel = UserFactory.userWithoutPassword();
        UserErrorResponse createUserErrorResponse = userApi.createUserApi(userModel)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract().as(UserErrorResponse.class);
        assertFalse(createUserErrorResponse.getSuccess());
        assertEquals("Email, password and name are required fields", createUserErrorResponse.getMessage());
    }
    @Test
    @DisplayName("Попытка создания пользователя без указания имени")
    void userCreateErrorWithoutNameTest(){
        userModel = UserFactory.userWithoutName();
        UserErrorResponse createUserErrorResponse = userApi.createUserApi(userModel)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract().as(UserErrorResponse.class);
        assertFalse(createUserErrorResponse.getSuccess());
        assertEquals("Email, password and name are required fields", createUserErrorResponse.getMessage());
    }

    @AfterEach
    void removeUser(){
        if(userModel!=null){
            userApi.removeUserApi(userModel);
        }
    }
}
