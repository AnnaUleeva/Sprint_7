package ru.yandex.praktikum;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.scooter_test.client.OrderApiClient;
import ru.yandex.praktikum.scooter_test.constants.OrderColors;
import ru.yandex.praktikum.scooter_test.helper.OrderGenerator;
import ru.yandex.praktikum.scooter_test.model.CreateOrderRequest;
import ru.yandex.praktikum.scooter_test.model.CreateOrderResponse;

import static org.apache.http.HttpStatus.SC_CREATED;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    OrderApiClient orderApiClient;
    CreateOrderRequest createOrderRequest;

    @Before
    public void setUp() {
        orderApiClient = new OrderApiClient();
    }

    public CreateOrderTest(CreateOrderRequest createOrderRequest) {
        this.createOrderRequest = createOrderRequest;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        String[] oneColor = {OrderColors.BLACK};
        String[] twoColor = {OrderColors.BLACK, OrderColors.GREY};
        String[] withoutColor = {};

        return new Object[][]{
                {OrderGenerator.getRandomOrder(oneColor)},
                {OrderGenerator.getRandomOrder(twoColor)},
                {OrderGenerator.getRandomOrder(withoutColor)},
        };
    }

    @Test
    public void CreateOrder() {
        Response createResponse = orderApiClient.create(createOrderRequest);
        assertEquals(SC_CREATED, createResponse.statusCode());
        CreateOrderResponse createOrderResponse = createResponse.as(CreateOrderResponse.class);
        assertNotNull(createOrderResponse.track);
    }
}
