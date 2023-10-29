package ru.yandex.praktikum;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.scooter_test.client.CourierApiClient;
import ru.yandex.praktikum.scooter_test.helper.CourierGenerator;
import ru.yandex.praktikum.scooter_test.helper.CourierHelper;
import ru.yandex.praktikum.scooter_test.model.CreateCourierRequest;
import ru.yandex.praktikum.scooter_test.model.CreateCourierResponse;
import ru.yandex.praktikum.scooter_test.model.LoginCourierRequest;
import ru.yandex.praktikum.scooter_test.model.LoginCourierResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class CourierLoginTest {
    CreateCourierRequest createCourierRequest;
    LoginCourierRequest loginCourierRequest;
    CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        loginCourierRequest = new LoginCourierRequest(createCourierRequest.login, createCourierRequest.password);
        courierApiClient = new CourierApiClient();
    }

    @Test
    public void CourierLogin() {
        CreateCourierResponse createCourierResponse = CourierHelper.create(createCourierRequest);
        assertTrue(createCourierResponse.ok);

        Response loginResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_OK, loginResponse.statusCode());
        LoginCourierResponse loginCourierResponse = loginResponse.as(LoginCourierResponse.class);
        assertNotNull(loginCourierResponse.id);
    }
}
