package ru.yandex.praktikum.scooter_test.client;

import io.restassured.response.Response;
import ru.yandex.praktikum.scooter_test.model.CreateOrderRequest;

import static ru.yandex.praktikum.scooter_test.config.ConfigApp.BASE_URL;

public class OrderApiClient extends BaseApiClient {
    public Response create(CreateOrderRequest createOrderRequest){
        return getPostSpec()
                .body(createOrderRequest)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }

    public Response getOrders(){
        return getPostSpec()
                .when()
                .get(BASE_URL + "/api/v1/orders");
    }
}
