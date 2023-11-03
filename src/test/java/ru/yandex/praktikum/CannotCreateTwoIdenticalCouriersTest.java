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

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;

public class CannotCreateTwoIdenticalCouriersTest {
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
    @DisplayName("Cannot crete two identical couriers")
    @Description("Создание двух курьеров с одинаковыми данными")
    public void cannotCreateTwoIdenticalCouriers() {
        Response createResponseOne = createFirstCourier();
        checkCreateCourierStatusCode(createResponseOne);
        CreateCourierResponse createCourierResponseOne = getCreateCourierResponse(createResponseOne);
        checkMessageCreateCourier(createCourierResponseOne);

        LoginCourierResponse loginCourierResponse = loginCourier();
        courierId = loginCourierResponse.getId();
        checkingCourierAuthorization(loginCourierResponse);


        Response createResponseTwo = createSecondCourier();
        checkCreateSecondCourierStatusCode(createResponseTwo);
        CreateCourierResponse createCourierResponseTwo = getCreateSecondCourierResponse(createResponseTwo);
        checkMessageCreateSecondCourier(createCourierResponseTwo);
    }

    @Step("Создание нового курьера")
    public Response createFirstCourier() {
        return courierApiClient.createCourier(createCourierRequest);
    }

    @Step("Метод для шага проверка кода при создании курьера")
    public void checkCreateCourierStatusCode(Response response) {
        assertEquals(SC_CREATED, response.statusCode());
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

    @Step("Создание курьера с данными первого курьера")
    public Response createSecondCourier() {
        return courierApiClient.createCourier(createCourierRequest);
    }

    @Step("Проверка статуса ошибки о создании второго курьера с данными ранее созданного курьера")
    public void checkCreateSecondCourierStatusCode(Response createResponseTwo) {
        assertEquals(SC_CONFLICT, createResponseTwo.statusCode());
    }

    @Step("Преобразование ответа к модели CreateCourierResponse")
    public CreateCourierResponse getCreateSecondCourierResponse(Response createResponseTwo) {
        return createResponseTwo.as(CreateCourierResponse.class);
    }

    @Step("Проверка сообщения о создании второго курьера с данными ранее созданного курьера")
    public void checkMessageCreateSecondCourier(CreateCourierResponse createCourierResponseTwo) {
        assertEquals("Этот логин уже используется. Попробуйте другой.", createCourierResponseTwo.getMessage());
    }

    @After
    public void reset() {
        CourierHelper.delete(courierId);
    }
}
