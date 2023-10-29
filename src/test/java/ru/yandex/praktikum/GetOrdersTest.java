package ru.yandex.praktikum;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.scooter_test.client.OrderApiClient;
import ru.yandex.praktikum.scooter_test.constants.OrderColors;
import ru.yandex.praktikum.scooter_test.helper.OrderGenerator;
import ru.yandex.praktikum.scooter_test.helper.OrderHelper;
import ru.yandex.praktikum.scooter_test.model.CreateOrderRequest;
import ru.yandex.praktikum.scooter_test.model.CreateOrderResponse;
import ru.yandex.praktikum.scooter_test.model.GetOrdersResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class GetOrdersTest {

    CreateOrderRequest createOrderRequest;
    OrderApiClient orderApiClient;

    @Before
    public void setUp() {
        String[] color = {OrderColors.BLACK};
        createOrderRequest = OrderGenerator.getRandomOrder(color);
        orderApiClient = new OrderApiClient();

    }

    @Test
    public void GetOrders() {
        CreateOrderResponse createOrderResponse = OrderHelper.create(createOrderRequest);
        assertNotNull(createOrderResponse.track);

        Response ordersResponse = orderApiClient.getOrders();
        assertEquals(SC_OK, ordersResponse.statusCode());
        GetOrdersResponse getOrdersResponse = ordersResponse.as(GetOrdersResponse.class);
        assertNotNull(getOrdersResponse.orders);
    }

}
