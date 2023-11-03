package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.scooter_test.client.CourierApiClient;
import ru.yandex.praktikum.scooter_test.helper.CourierGenerator;
import ru.yandex.praktikum.scooter_test.model.CreateCourierRequest;
import ru.yandex.praktikum.scooter_test.model.LoginCourierRequest;
import ru.yandex.praktikum.scooter_test.model.LoginCourierResponse;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

public class CourierLoginWithEmptyFieldTest {
    private LoginCourierRequest loginCourierRequest;
    private CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        CreateCourierRequest createCourierRequest = CourierGenerator.getRandomCourier();
        loginCourierRequest = new LoginCourierRequest("", createCourierRequest.getPassword());
        courierApiClient = new CourierApiClient();
    }

    @Test
    @DisplayName("Login courier with empty field")
    @Description("Авторизация курьера с пустым полем логин")
    public void courierLoginWithEmptyField() {
        Response loginResponse = loginCourier();
        checkingCourierAuthorization(loginResponse);
        LoginCourierResponse loginCourierResponse = getLoginCourier(loginResponse);
        checkMessageLoginSecondCourier(loginCourierResponse);
    }

    @Step("Авторизация курьера")
    public Response loginCourier() {
        return courierApiClient.loginCourier(loginCourierRequest);
    }

    @Step("Проверка отображения Id авторизованного курьера")
    public void checkingCourierAuthorization(Response loginResponse) {
        assertEquals(SC_BAD_REQUEST, loginResponse.statusCode());
    }

    @Step("Преобразование ответа к модели LoginCourierResponse")
    public LoginCourierResponse getLoginCourier(Response loginResponse) {
        return loginResponse.as(LoginCourierResponse.class);
    }

    @Step("Проверка сообщения о попытке авторизации курьера с пустым полем логин")
    public void checkMessageLoginSecondCourier(LoginCourierResponse loginCourierResponse) {
        assertEquals("Недостаточно данных для входа", loginCourierResponse.getMessage());
    }
}
