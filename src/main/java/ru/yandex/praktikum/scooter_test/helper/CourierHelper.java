package ru.yandex.praktikum.scooter_test.helper;

import ru.yandex.praktikum.scooter_test.client.CourierApiClient;
import ru.yandex.praktikum.scooter_test.model.*;

import static org.apache.http.HttpStatus.*;

public class CourierHelper {
    static CourierApiClient courierApiClient = new CourierApiClient();

    public static LoginCourierResponse login(LoginCourierRequest loginCourierRequest) {
        return courierApiClient.loginCourier(loginCourierRequest).then().statusCode(SC_OK).and().extract().as(LoginCourierResponse.class);
    }

    public static CreateCourierResponse create(CreateCourierRequest createCourierRequest) {
        return courierApiClient.createCourier(createCourierRequest).then().statusCode(SC_CREATED).and().extract().as(CreateCourierResponse.class);
    }

    public static DeleteCourierResponse delete(Integer id){
        return courierApiClient.deleteCourier(id).then().statusCode(SC_OK).and().extract().as(DeleteCourierResponse.class);
    }
}
