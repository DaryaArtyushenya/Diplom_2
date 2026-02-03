package tests;

import api.OrderApi;
import api.UserApi;
import com.google.gson.Gson;
import factory.IngredientsFactory;
import factory.UserFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.data.UserModel;
import models.pojo.pojoOrder.CreateOrderAuthorizeUserResponse;
import models.pojo.pojoOrder.CreateOrderRequest;
import models.pojo.pojoOrder.CreateOrderResponseGuest;
import models.pojo.pojoOrder.OrderResponseError;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateOrderTests {
    @BeforeEach
    void setUp(){
        RestAssured.baseURI = "https://stellarburgers.education-services.ru";
    }
    UserApi userApi = new UserApi();
    OrderApi orderApi = new OrderApi();
    @Test
    @DisplayName("Создание ордера для неавторизованного пользователя")
    void successOrderForGuestTest(){
        CreateOrderRequest createOrderRequest = IngredientsFactory.validOrder();
        CreateOrderResponseGuest createOrderResponseGuest = orderApi.createOrderApi(createOrderRequest)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(CreateOrderResponseGuest.class);
        System.out.println("ответ для гостя" + new Gson().toJson(createOrderResponseGuest));
        assertTrue(createOrderResponseGuest.getSuccess());
        assertNotNull(createOrderResponseGuest.getOrder());
        assertNotNull(createOrderResponseGuest.getName());
    }
    @Test
    @DisplayName("Создание ордера для авторизованного пользователя")
    void successOrderForAuthorizeUserTest(){
        UserModel userModel = UserFactory.correctUser();
        CreateOrderRequest createOrderRequest = IngredientsFactory.validOrder();
        userApi.createUserApi(userModel);
        Response response = userApi.loginUserApi(userModel);
        String token = response.jsonPath().getString("accessToken");
        CreateOrderAuthorizeUserResponse createOrderAuthorizeUserResponse = orderApi.createOrderAuthorizeUserApi(createOrderRequest, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(CreateOrderAuthorizeUserResponse.class);
        assertTrue(createOrderAuthorizeUserResponse.getSuccess());
        assertNotNull(createOrderAuthorizeUserResponse.getOrder());
        assertNotNull(createOrderAuthorizeUserResponse.getName());
        userApi.removeUserApi(userModel);
    }
    @Test
    @DisplayName("Попытка создать ордер с невалидными ингредиентами")
    void createOrderWithNullIngredientsTest(){
        UserModel userModel = UserFactory.correctUser();
        CreateOrderRequest createOrderRequest = IngredientsFactory.nullIngredients();
        userApi.createUserApi(userModel);
        Response response = userApi.loginUserApi(userModel);
        String token = response.jsonPath().getString("accessToken");
        orderApi.createOrderAuthorizeUserApi(createOrderRequest, token)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        userApi.removeUserApi(userModel);
    }
    @Test
    @DisplayName("Попытка создания ордера с несуществующими ингредиентами")
    void createOrderWithInvalidIngredientsHashTest(){
        UserModel userModel = UserFactory.correctUser();
        CreateOrderRequest createOrderRequest = IngredientsFactory.invalidIngredientHash();
        System.out.println(new Gson().toJson(createOrderRequest));
        userApi.createUserApi(userModel);
        Response response = userApi.loginUserApi(userModel);
        String token = response.jsonPath().getString("accessToken");
        OrderResponseError orderResponseError = orderApi.createOrderAuthorizeUserApi(createOrderRequest, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST).extract().as(OrderResponseError.class);
        assertFalse(orderResponseError.getSuccess());
        assertEquals("One or more ids provided are incorrect", orderResponseError.getMessage() );
        userApi.removeUserApi(userModel);

    }
    @Test
    @DisplayName("Попытка создать ордер не передав ингредиенты")
    void createOrderWithoutIngredientsTest(){
        UserModel userModel = UserFactory.correctUser();
        CreateOrderRequest createOrderRequest = IngredientsFactory.orderWithoutIngredients();
        System.out.println(new Gson().toJson(createOrderRequest));
        userApi.createUserApi(userModel);
        Response response = userApi.loginUserApi(userModel);
        String token = response.jsonPath().getString("accessToken");
        OrderResponseError orderResponseError = orderApi.createOrderAuthorizeUserApi(createOrderRequest, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST).extract().as(OrderResponseError.class);
        assertFalse(orderResponseError.getSuccess());
        assertEquals("Ingredient ids must be provided", orderResponseError.getMessage() );
        userApi.removeUserApi(userModel);

    }

}
