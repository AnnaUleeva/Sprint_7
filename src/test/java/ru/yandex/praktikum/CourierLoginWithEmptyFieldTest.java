package ru.yandex.praktikum;

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
    LoginCourierRequest loginCourierRequest;
    CourierApiClient courierApiClient;

    @Before
    public void setUp(){
        CreateCourierRequest createCourierRequest = CourierGenerator.getRandomCourier();
        loginCourierRequest = new LoginCourierRequest("", createCourierRequest.password);
        courierApiClient = new CourierApiClient();
    }

    @Test
    public void CourierLoginWithEmptyField(){
        Response loginResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_BAD_REQUEST, loginResponse.statusCode());
        LoginCourierResponse loginCourierResponse = loginResponse.as(LoginCourierResponse.class);
        assertEquals("Недостаточно данных для входа", loginCourierResponse.message);

    }
}
