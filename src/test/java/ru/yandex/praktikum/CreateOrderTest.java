package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
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
    private OrderApiClient orderApiClient;
    private final CreateOrderRequest createOrderRequest;

    @Before
    public void setUp() {

        orderApiClient = new OrderApiClient();
    }

    public CreateOrderTest(CreateOrderRequest createOrderRequest) {

        this.createOrderRequest = createOrderRequest;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        OrderColors[] blackColor = {OrderColors.BLACK};
        OrderColors[] greyColor = {OrderColors.GREY};
        OrderColors[] twoColor = {OrderColors.BLACK, OrderColors.GREY};
        OrderColors[] withoutColor = {};


        return new Object[][]{
                {OrderGenerator.getRandomOrder(blackColor)},
                {OrderGenerator.getRandomOrder(greyColor)},
                {OrderGenerator.getRandomOrder(twoColor)},
                {OrderGenerator.getRandomOrder(withoutColor)},
        };
    }

    @Test
    @DisplayName("Create order")
    @Description("Создание заказа")
    public void createOrder() {
        Response createResponse = createNewOrder(createOrderRequest);
        checkStatusCodeCreateOrder(createResponse);
        CreateOrderResponse createOrderResponse = getCreateOrderResponse(createResponse);
        checkTrackOutput(createOrderResponse);
    }

    @Step("Создание заказа")
    public Response createNewOrder(CreateOrderRequest createOrderRequest) {
        return orderApiClient.create(createOrderRequest);
    }

    @Step("Проверка выхода статус кода на создание заказа")
    public void checkStatusCodeCreateOrder(Response createResponse) {
        assertEquals(SC_CREATED, createResponse.statusCode());
    }

    @Step("Преобразование ответа к модели CreateOrderResponse")
    public CreateOrderResponse getCreateOrderResponse(Response createResponse) {
        return createResponse.as(CreateOrderResponse.class);
    }

    @Step("Проверка выхода track")
    public void checkTrackOutput(CreateOrderResponse createOrderResponse) {
        assertNotNull(createOrderResponse.getTrack());
    }
}

