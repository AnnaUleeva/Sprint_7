package ru.yandex.praktikum.scooter_test.client;


import io.restassured.response.Response;
import ru.yandex.praktikum.scooter_test.model.CreateCourierRequest;
import ru.yandex.praktikum.scooter_test.model.LoginCourierRequest;

import static ru.yandex.praktikum.scooter_test.config.ConfigApp.BASE_URL;

public class CourierApiClient extends BaseApiClient {
    public Response createCourier(CreateCourierRequest createCourierRequest) {
        return getPostSpec()
                .body(createCourierRequest)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response loginCourier(LoginCourierRequest loginCourierRequest) {
        return getPostSpec()
                .body(loginCourierRequest)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }

    public Response deleteCourier(Integer id) {
        return getPostSpec()
                .when()
                .delete(BASE_URL + "/api/v1/courier/" + id);
    }
}
