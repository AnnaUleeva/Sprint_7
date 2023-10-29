package ru.yandex.praktikum.scooter_test.helper;

import ru.yandex.praktikum.scooter_test.client.OrderApiClient;
import ru.yandex.praktikum.scooter_test.model.CreateOrderRequest;
import ru.yandex.praktikum.scooter_test.model.CreateOrderResponse;

import static org.apache.http.HttpStatus.SC_CREATED;

public class OrderHelper {
    static OrderApiClient orderApiClient = new OrderApiClient();
    public static CreateOrderResponse create(CreateOrderRequest createOrderRequest) {
        return orderApiClient.create(createOrderRequest).then().statusCode(SC_CREATED).and().extract().as(CreateOrderResponse.class);
    }
}
