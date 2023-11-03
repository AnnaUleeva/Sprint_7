package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.scooter_test.client.CourierApiClient;
import ru.yandex.praktikum.scooter_test.model.CreateCourierRequest;
import ru.yandex.praktikum.scooter_test.model.CreateCourierResponse;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

public class CreatingCourierWithoutRequiredFieldsTest {
    private CreateCourierRequest createCourierRequest;
    private CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        createCourierRequest = new CreateCourierRequest();
        courierApiClient = new CourierApiClient();
    }

    @Test
    @DisplayName("Creating courier without required fields")
    @Description("Создание курьера без обязательных полей")
    public void creatingCourierWithoutRequiredFields() {
        Response createResponse = createCourierEmptyFailed();
        checkCreateCourierEmptyStatusCode(createResponse);
        CreateCourierResponse createCourierResponse = getCreateCourierEmptyFailed(createResponse);
        checkMessageCreateCourier(createCourierResponse);
    }

    @Step("Создание курьера без обязательных полей")
    public Response createCourierEmptyFailed() {
        return courierApiClient.createCourier(createCourierRequest);
    }

    @Step("Проверка статус кода о создание курьера без обязательных полей")
    public void checkCreateCourierEmptyStatusCode(Response createResponse) {
        assertEquals(SC_BAD_REQUEST, createResponse.statusCode());
    }

    @Step("Преобразование ответа к модели CreateCourierResponse")
    public CreateCourierResponse getCreateCourierEmptyFailed(Response createResponse) {
        return createResponse.as(CreateCourierResponse.class);
    }

    @Step("Проверка сообщения о создании клиента без обязательных полей")
    public void checkMessageCreateCourier(CreateCourierResponse createCourierResponse) {
        assertEquals("Недостаточно данных для создания учетной записи", createCourierResponse.getMessage());
    }
}
