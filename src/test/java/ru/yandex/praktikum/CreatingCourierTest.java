package ru.yandex.praktikum;

import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;

import org.junit.Before;
import ru.yandex.praktikum.scooter_test.client.CourierApiClient;
import ru.yandex.praktikum.scooter_test.helper.CourierHelper;
import ru.yandex.praktikum.scooter_test.model.CreateCourierRequest;
import ru.yandex.praktikum.scooter_test.model.CreateCourierResponse;
import ru.yandex.praktikum.scooter_test.model.LoginCourierRequest;
import ru.yandex.praktikum.scooter_test.helper.CourierGenerator;
import ru.yandex.praktikum.scooter_test.model.LoginCourierResponse;


public class CreatingCourierTest {
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
    public void CreatingCourier() {
        Response createResponse = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_CREATED, createResponse.statusCode());
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.ok);

        LoginCourierResponse loginCourierResponse = CourierHelper.login(loginCourierRequest);
        assertNotNull(loginCourierResponse.id);
    }
}
