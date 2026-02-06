package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.pojo.pojoOrder.CreateOrderRequest;

import static io.restassured.RestAssured.given;

public class OrderApi {
    @Step("Создание заказа для гостя")
    public Response createOrderApi(CreateOrderRequest createOrderRequest){
        return given()
                .header("Content-type","application/json")
                .body(createOrderRequest)
                .post("/api/orders");
    }
    @Step("Создание заказа для гостя")
    public Response createOrderAuthorizeUserApi(CreateOrderRequest createOrderRequest, String token){
        return given()
                .header("Content-type","application/json")
                .header("Authorization" , token)
                .body(createOrderRequest)
                .post("/api/orders");
    }
    @Step("Список ордеров для гостя")
    public Response getOrders(){
        return given()
                .get("api/orders/all");
    }
    @Step("Список ордеров для авторизованного пользователя")
    public Response getOrdersAuthorizeUser(String token){
        return given()
                .header("Authorization", token)
                .get("api/orders/all");
    }


}
