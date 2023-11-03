package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
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

    private CreateCourierRequest createCourierRequest;
    private LoginCourierRequest loginCourierRequest;
    private CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        loginCourierRequest = new LoginCourierRequest(createCourierRequest.getLogin(), createCourierRequest.getPassword());
        courierApiClient = new CourierApiClient();
    }

    @Test
    @DisplayName("Courier login non existen user")
    @Description("Авторизация курьера с несуществующими данными")
    public void courierLoginNonExistentUser() {
        CreateCourierResponse createCourierResponse = createNewCourier();
        checkCreateCourierStatusCode(createCourierResponse);
        LoginCourierResponse loginCourierResponse = loginCourier();
        checkingCourierAuthorization(loginCourierResponse);
        DeleteCourierResponse deleteCourierResponse = deleteCourier(loginCourierResponse);
        checkDeleteCourierStatus(deleteCourierResponse);

        Response loginNotExsistResponse = createSecondCourier();
        checkCreateTwoCourierStatusCode(loginNotExsistResponse);
        LoginCourierResponse loginNotExistCourierResponse = getCreateSecondCourier(loginNotExsistResponse);
        checkMessageLoginSecondCourier(loginNotExistCourierResponse);
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

    @Step("Удаление созданного курьера ")
    public DeleteCourierResponse deleteCourier(LoginCourierResponse loginCourierResponse) {
        return CourierHelper.delete(loginCourierResponse.getId());
    }

    @Step("Проверка статуса удаления курьера")
    public void checkDeleteCourierStatus(DeleteCourierResponse deleteCourierResponse) {
        assertTrue(deleteCourierResponse.getOk());
    }

    @Step("Авторизация курьера с несуществующими данными")
    public Response createSecondCourier() {
        return courierApiClient.loginCourier(loginCourierRequest);
    }

    @Step("Проверка статуса ошибки о попытке авторизации курьера с несуществующими данными")
    public void checkCreateTwoCourierStatusCode(Response loginNotExsistResponse) {
        assertEquals(SC_NOT_FOUND, loginNotExsistResponse.statusCode());
    }

    @Step("Преобразование ответа к модели LoginCourierResponse")
    public LoginCourierResponse getCreateSecondCourier(Response loginNotExsistResponse) {
        return loginNotExsistResponse.as(LoginCourierResponse.class);
    }

    @Step("Проверка сообщения о попытке авторизации курьера с несуществующими данными")
    public void checkMessageLoginSecondCourier(LoginCourierResponse loginNotExistCourierResponse) {
        assertEquals("Учетная запись не найдена", loginNotExistCourierResponse.getMessage());
    }
}

