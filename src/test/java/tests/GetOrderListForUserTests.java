package tests;

import api.OrderApi;
import api.UserApi;
import factory.UserFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.data.UserModel;
import models.pojo.pojoOrder.OrderListResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetOrderListForUserTests {
    @BeforeEach
    void setUp(){
        RestAssured.baseURI = "https://stellarburgers.education-services.ru";
    }

    OrderApi orderApi = new OrderApi();
    UserApi userApi = new UserApi();
    private UserModel userModel;
    @Test
    @DisplayName("Получение списка ордеров для неавторизованного пользователя")
    void getOrdersForGuestTest(){
        OrderListResponse orderListResponse = orderApi.getOrders()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(OrderListResponse.class);
        assertTrue(orderListResponse.getSuccess());
        assertNotNull(orderListResponse.getOrders());
        assertNotNull(orderListResponse.getTotal());
        assertNotNull(orderListResponse.getTotalToday());
    }
    @Test
    @DisplayName("Получение списка ордеров авторизованного пользователя")
    void getOrdersForAuthorizeUserTest(){
        userModel = UserFactory.correctUser();
        userApi.createUserApi(userModel);
        Response response = userApi.loginUserApi(userModel);
        String token = response.jsonPath().getString("accessToken");
        OrderListResponse orderListResponse = orderApi.getOrdersAuthorizeUser(token)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(OrderListResponse.class);
        assertTrue(orderListResponse.getSuccess());
        assertNotNull(orderListResponse.getOrders());
        assertNotNull(orderListResponse.getTotal());
        assertNotNull(orderListResponse.getTotalToday());
    }
    @AfterEach
    void removeUser(){
        if(userModel!=null){
            userApi.removeUserApi(userModel);
        }
    }
}
