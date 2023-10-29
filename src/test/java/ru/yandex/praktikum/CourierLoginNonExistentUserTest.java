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

public class CourierLoginNonExistentUserTest {

    CreateCourierRequest createCourierRequest;
    LoginCourierRequest loginCourierRequest;
    CourierApiClient courierApiClient;

    @Before
    public void setUp(){
        createCourierRequest = CourierGenerator.getRandomCourier();
        loginCourierRequest = new LoginCourierRequest(createCourierRequest.login, createCourierRequest.password);
        courierApiClient = new CourierApiClient();

    }

    @Test
    public void CourierLoginNonExistentUser(){
        CreateCourierResponse createCourierResponse = CourierHelper.create(createCourierRequest);
        assertTrue(createCourierResponse.ok);
        LoginCourierResponse loginCourierResponse = CourierHelper.login(loginCourierRequest);
        assertNotNull(loginCourierResponse.id);
        DeleteCourierResponse deleteCourierResponse = CourierHelper.delete(loginCourierResponse.id);
        assertTrue(deleteCourierResponse.ok);

        Response loginNotExsistResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_NOT_FOUND, loginNotExsistResponse.statusCode());
        LoginCourierResponse loginNotExistCourierResponse = loginNotExsistResponse.as(LoginCourierResponse.class);
        assertEquals("Учетная запись не найдена", loginNotExistCourierResponse.message);
    }
}
