package ru.yandex.praktikum.scooter_test.client;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.scooter_test.config.ConfigApp.BASE_URL;

public class BaseApiClient {
    public RequestSpecification getPostSpec() {
        return given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
    }
}
