package ru.yandex.praktikum.scooter_test.client;


import io.restassured.response.Response;
import ru.yandex.praktikum.scooter_test.model.CreateCourierRequest;
import ru.yandex.praktikum.scooter_test.model.LoginCourierRequest;

public class CourierApiClient extends BaseApiClient {
    final static String API_CLIENT = "/api/v1/courier";

    public Response createCourier(CreateCourierRequest createCourierRequest) {
        return getPostSpec()
                .body(createCourierRequest)
                .when()
                .post(API_CLIENT);
    }

    public Response loginCourier(LoginCourierRequest loginCourierRequest) {
        return getPostSpec()
                .body(loginCourierRequest)
                .when()
                .post(API_CLIENT + "/login");
    }

    public Response deleteCourier(Integer id) {
        return getPostSpec()
                .when()
                .delete(API_CLIENT + "/" + id);
    }
}
