package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetOrdersTest {

    private CreateOrderRequest createOrderRequest;
    private OrderApiClient orderApiClient;

    @Before
    public void setUp() {
        OrderColors[] color = {OrderColors.BLACK};
        createOrderRequest = OrderGenerator.getRandomOrder(color);
        orderApiClient = new OrderApiClient();

    }

    @Test
    @DisplayName("Get orders")
    @Description("Получение списка заказов")
    public void getOrders() {
        CreateOrderResponse createOrderResponse = createOrder();
        checkTrackResponse(createOrderResponse);

        Response ordersResponse = getListOrders();
        checkStatusOkGetList(ordersResponse);
        GetOrdersResponse getOrdersResponse = getCreateListOrdersResponse(ordersResponse);
        checkCreateListOrderResponse(getOrdersResponse);
    }

    @Step("Создание заказа")
    public CreateOrderResponse createOrder() {
        return OrderHelper.create(createOrderRequest);
    }

    @Step("Проверка выхода track о создании заказа")
    public void checkTrackResponse(CreateOrderResponse createOrderResponse) {
        assertNotNull(createOrderResponse.getTrack());
    }

    @Step("Получение списка заказов")
    public Response getListOrders() {
        return orderApiClient.getOrders();
    }

    @Step("Проверка выхода статус сообщения о получении списка заказов")
    public void checkStatusOkGetList(Response ordersResponse) {
        assertEquals(SC_OK, ordersResponse.statusCode());
    }

    @Step("Преобразование ответа к модели GetOrdersResponse")
    public GetOrdersResponse getCreateListOrdersResponse(Response ordersResponse) {
        return ordersResponse.as(GetOrdersResponse.class);
    }

    @Step("Проверка преобразованного ответа")
    public void checkCreateListOrderResponse(GetOrdersResponse getOrdersResponse) {
        assertNotNull(getOrdersResponse.getOrders());
    }


}
