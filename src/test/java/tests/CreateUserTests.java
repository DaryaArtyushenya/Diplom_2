package tests;

import factory.UserFactory;
import api.UserApi;
import io.restassured.RestAssured;
import models.data.UserModel;
import models.pojo.CreateLoginUserResponse;
import models.pojo.UserErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTests {
    @BeforeEach
    void setUp(){
        RestAssured.baseURI="https://stellarburgers.education-services.ru/";
    }
    UserApi userApi = new UserApi();

    @Test
    @DisplayName("Пользователь успешно создан со всеми обязательными полями")
    void successUserCreationTest(){
        UserModel userModel = UserFactory.correctUser();
      CreateLoginUserResponse createLoginUserResponse = userApi.createUserApi(userModel)
             .then().log().all()
             .statusCode(200)
                     .extract().as(CreateLoginUserResponse.class);
        assertNotNull(createLoginUserResponse.getAccessToken());
        assertNotNull(createLoginUserResponse.getRefreshToken());
        assertNotNull(createLoginUserResponse.getUser());
        assertNotNull(createLoginUserResponse.getSuccess());
        userApi.removeUserApi(userModel);
}
@Test
@DisplayName("Такой пользователь уже существует")
    void userAlreadyExistTest(){
    UserModel userModel = UserFactory.correctUser();
    userApi.createUserApi(userModel);
    UserErrorResponse userErrorResponse = userApi.createUserApi(userModel)
            .then()
            .statusCode(403)
            .extract().as(UserErrorResponse.class);
    assertFalse(userErrorResponse.getSuccess());
    assertEquals("User already exists", userErrorResponse.getMessage());
    userApi.removeUserApi(userModel);
}
@Test
@DisplayName("Попытка создания пользователя без ввода имейла")
    void userCreateErrorWithoutEmailTest(){
        UserModel userModel = UserFactory.userWithoutEmail();
        UserErrorResponse createUserErrorResponse = userApi.createUserApi(userModel)
                .then()
                .statusCode(403)
                .extract().as(UserErrorResponse.class);
    assertFalse(createUserErrorResponse.getSuccess());
    assertEquals("Email, password and name are required fields", createUserErrorResponse.getMessage());
}
    @Test
    @DisplayName("Попытка создания пользователя без ввода пароля")
    void userCreateErrorWithoutPasswordTest(){
        UserModel userModel = UserFactory.userWithoutPassword();
        UserErrorResponse createUserErrorResponse = userApi.createUserApi(userModel)
                .then()
                .statusCode(403)
                .extract().as(UserErrorResponse.class);
        assertFalse(createUserErrorResponse.getSuccess());
        assertEquals("Email, password and name are required fields", createUserErrorResponse.getMessage());
    }
    @Test
    @DisplayName("Попытка создания пользователя без указания имени")
    void userCreateErrorWithoutNameTest(){
        UserModel userModel = UserFactory.userWithoutName();
        UserErrorResponse createUserErrorResponse = userApi.createUserApi(userModel)
                .then()
                .statusCode(403)
                .extract().as(UserErrorResponse.class);
        assertFalse(createUserErrorResponse.getSuccess());
        assertEquals("Email, password and name are required fields", createUserErrorResponse.getMessage());
    }

}
