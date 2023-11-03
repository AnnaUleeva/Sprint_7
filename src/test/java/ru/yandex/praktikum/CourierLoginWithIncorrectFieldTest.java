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

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.*;

public class CourierLoginWithIncorrectFieldTest {
    private CreateCourierRequest createCourierRequest;
    private LoginCourierRequest loginCourierRequest;
    private LoginCourierRequest loginIncorrectCourierRequest;
    private CourierApiClient courierApiClient;
    private Integer courierId;

    @Before
    public void setUp() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        loginCourierRequest = new LoginCourierRequest(createCourierRequest.getLogin(), createCourierRequest.getPassword());
        loginIncorrectCourierRequest = new LoginCourierRequest(createCourierRequest.getLogin(), "****");
        courierApiClient = new CourierApiClient();
    }

    @Test
    @DisplayName("Courier login with incorrect field")
    @Description("Авторизация курьера с некорректным паролем")
    public void courierLoginWithIncorrectField() {
        CreateCourierResponse createCourierResponse = createNewCourier();
        checkCreateCourierStatusCode(createCourierResponse);
        LoginCourierResponse loginCourierResponse = loginCourier();
        checkingCourierAuthorization(loginCourierResponse);
        courierId = loginCourierResponse.getId();

        Response loginIncorrectResponse = loginCourierWithIncorrect();
        checkStatusCodeLogin(loginIncorrectResponse);
        LoginCourierResponse loginIncorrectCourierResponse = getLoginCourierResponse(loginIncorrectResponse);
        checkMessageLoginCourier(loginIncorrectCourierResponse);
    }

    @Step("Создание нового курьера")
    public CreateCourierResponse createNewCourier() {
        return CourierHelper.create(createCourierRequest);
    }

    @Step("Проверка кода при создании курьера")
    public void checkCreateCourierStatusCode(CreateCourierResponse createCourierResponse) {
        assertTrue(createCourierResponse.getOk());
    }

    @Step("Авторизация курьера")
    public LoginCourierResponse loginCourier() {
        return CourierHelper.login(loginCourierRequest);
    }

    @Step("Проверка отображения Id вторизованного курьера")
    public void checkingCourierAuthorization(LoginCourierResponse loginCourierResponse) {
        assertNotNull(loginCourierResponse.getId());
    }

    @Step("Авторизация курьера с некорректынм паролем")
    public Response loginCourierWithIncorrect() {
        return courierApiClient.loginCourier(loginIncorrectCourierRequest);
    }

    @Step("Проверка статус кода при авторизации")
    public void checkStatusCodeLogin(Response loginIncorrectResponse) {
        assertEquals(SC_NOT_FOUND, loginIncorrectResponse.statusCode());
    }

    @Step("Преобразование ответа к модели LoginCourierResponse")
    public LoginCourierResponse getLoginCourierResponse(Response loginIncorrectResponse) {
        return loginIncorrectResponse.as(LoginCourierResponse.class);
    }

    @Step("Проверка сообщений о ненайденном пользователе при авторизации")
    public void checkMessageLoginCourier(LoginCourierResponse loginIncorrectCourierResponse) {
        assertEquals("Учетная запись не найдена", loginIncorrectCourierResponse.getMessage());
    }

    @After
    public void reset() {
        CourierHelper.delete(courierId);
    }
}

