package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.scooter_test.client.CourierApiClient;
import ru.yandex.praktikum.scooter_test.helper.CourierGenerator;
import ru.yandex.praktikum.scooter_test.helper.CourierHelper;
import ru.yandex.praktikum.scooter_test.model.CreateCourierRequest;
import ru.yandex.praktikum.scooter_test.model.CreateCourierResponse;
import ru.yandex.praktikum.scooter_test.model.LoginCourierRequest;
import ru.yandex.praktikum.scooter_test.model.LoginCourierResponse;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;


public class CreatingCourierTest {
    private CreateCourierRequest createCourierRequest;
    private LoginCourierRequest loginCourierRequest;
    private CourierApiClient courierApiClient;
    private Integer courierId;

    @Before
    public void setUp() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        loginCourierRequest = new LoginCourierRequest(createCourierRequest.getLogin(), createCourierRequest.getPassword());
        courierApiClient = new CourierApiClient();
    }

    @Test
    @DisplayName("Creating courier")
    @Description("Создание курьера ")
    public void creatingCourier() {
        Response createResponse = createCourier();
        checkCreateCourierStatusCode(createResponse);
        CreateCourierResponse createCourierResponse = getCreateCourierResponse(createResponse);
        checkMessageCreateCourier(createCourierResponse);

        LoginCourierResponse loginCourierResponse = loginCourier();
        checkingCourierAuthorization(loginCourierResponse);
        courierId = loginCourierResponse.getId();
    }

    @Step("Создание нового курьера")
    public Response createCourier() {
        return courierApiClient.createCourier(createCourierRequest);
    }

    @Step("Метод для шага проверка кода при создании курьера")
    public void checkCreateCourierStatusCode(Response createResponse) {
        assertEquals(SC_CREATED, createResponse.statusCode());
    }

    @Step("Преобразование ответа к модели CreateCourierResponse")
    public CreateCourierResponse getCreateCourierResponse(Response response) {
        return response.as(CreateCourierResponse.class);
    }

    @Step("Проверка сообщения о создании первого курьера")
    public void checkMessageCreateCourier(CreateCourierResponse response) {
        assertTrue(response.getOk());
    }

    @Step("Авторизация курьера")
    public LoginCourierResponse loginCourier() {
        return CourierHelper.login(loginCourierRequest);
    }

    @Step("Проверка отображения Id вторизованного курьера")
    public void checkingCourierAuthorization(LoginCourierResponse loginCourierResponse) {
        assertNotNull(loginCourierResponse.getId());
    }

    @After
    public void reset() {
        CourierHelper.delete(courierId);
    }

}
