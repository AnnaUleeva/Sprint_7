package ru.yandex.praktikum;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.scooter_test.client.CourierApiClient;
import ru.yandex.praktikum.scooter_test.helper.CourierGenerator;
import ru.yandex.praktikum.scooter_test.helper.CourierHelper;
import ru.yandex.praktikum.scooter_test.model.*;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class CourierLoginWithIncorrectFieldTest {
    CreateCourierRequest createCourierRequest;
    LoginCourierRequest loginCourierRequest;
    LoginCourierRequest loginIncorrectCourierRequest;
    CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        loginCourierRequest = new LoginCourierRequest(createCourierRequest.login, createCourierRequest.password);
        loginIncorrectCourierRequest = new LoginCourierRequest(createCourierRequest.login, "****");
        courierApiClient = new CourierApiClient();
    }

    @Test
    public void CourierLoginWithIncorrectField() {
        CreateCourierResponse createCourierResponse = CourierHelper.create(createCourierRequest);
        assertTrue(createCourierResponse.ok);
        LoginCourierResponse loginCourierResponse = CourierHelper.login(loginCourierRequest);
        assertNotNull(loginCourierResponse.id);

        Response loginIncorrectResponse = courierApiClient.loginCourier(loginIncorrectCourierRequest);
        assertEquals(SC_NOT_FOUND, loginIncorrectResponse.statusCode());
        LoginCourierResponse loginIncorrectCourierResponse = loginIncorrectResponse.as(LoginCourierResponse.class);
        assertEquals("Учетная запись не найдена", loginIncorrectCourierResponse.message);
    }
}
