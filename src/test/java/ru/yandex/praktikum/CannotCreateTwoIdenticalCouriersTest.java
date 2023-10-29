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

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;

public class CannotCreateTwoIdenticalCouriersTest {
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
    public void CannotCreateTwoIdenticalCouriers(){
        Response createResponseOne = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_CREATED, createResponseOne.statusCode());
        CreateCourierResponse createCourierResponseOne = createResponseOne.as(CreateCourierResponse.class);
        assertTrue(createCourierResponseOne.ok);

        LoginCourierResponse loginCourierResponse = CourierHelper.login(loginCourierRequest);
        assertNotNull(loginCourierResponse.id);

        Response createResponseTwo = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_CONFLICT, createResponseTwo.statusCode());
        CreateCourierResponse createCourierResponseTwo = createResponseTwo.as(CreateCourierResponse.class);
        assertEquals("Этот логин уже используется. Попробуйте другой.", createCourierResponseTwo.message);
    }

}
