package ru.yandex.praktikum;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.scooter_test.client.CourierApiClient;
import ru.yandex.praktikum.scooter_test.model.CreateCourierRequest;
import ru.yandex.praktikum.scooter_test.model.CreateCourierResponse;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

import static org.junit.Assert.assertEquals;

public class CreatingCourierWithoutRequiredFieldsTest {
    CreateCourierRequest createCourierRequest;
    CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        createCourierRequest = new CreateCourierRequest();
        courierApiClient = new CourierApiClient();
    }

    @Test
    public void CreatingCourierWithoutRequiredFields() {
        Response createResponse = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_BAD_REQUEST, createResponse.statusCode());
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);

        assertEquals("Недостаточно данных для создания учетной записи", createCourierResponse.message);
    }
}
