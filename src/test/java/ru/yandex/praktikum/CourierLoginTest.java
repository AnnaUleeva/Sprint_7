package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.scooter_test.helper.CourierGenerator;
import ru.yandex.praktikum.scooter_test.helper.CourierHelper;
import ru.yandex.praktikum.scooter_test.model.CreateCourierRequest;
import ru.yandex.praktikum.scooter_test.model.CreateCourierResponse;
import ru.yandex.praktikum.scooter_test.model.LoginCourierRequest;
import ru.yandex.praktikum.scooter_test.model.LoginCourierResponse;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CourierLoginTest {
    private CreateCourierRequest createCourierRequest;
    private LoginCourierRequest loginCourierRequest;
    private Integer courierId;

    @Before
    public void setUp() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        loginCourierRequest = new LoginCourierRequest(createCourierRequest.getLogin(), createCourierRequest.getPassword());
    }

    @Test
    @DisplayName("Courier login")
    @Description("Авторизация курьера")
    public void courierLogin() {
        CreateCourierResponse createCourierResponse = createNewCourier();
        checkCreateCourierStatusCode(createCourierResponse);
        LoginCourierResponse loginCourierResponse = loginCourier();
        checkingCourierAuthorization(loginCourierResponse);
        courierId = loginCourierResponse.getId();
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

    @Step("Проверка отображения Id авторизованного курьера")
    public void checkingCourierAuthorization(LoginCourierResponse loginCourierResponse) {
        assertNotNull(loginCourierResponse.getId());
    }

    @After
    public void reset() {
        CourierHelper.delete(courierId);
    }
}
