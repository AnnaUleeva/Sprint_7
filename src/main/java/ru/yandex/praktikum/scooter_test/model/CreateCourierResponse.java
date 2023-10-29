package ru.yandex.praktikum.scooter_test.model;

public class CreateCourierResponse {
    public Boolean ok;
    public String message;

    public CreateCourierResponse(Boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }
}
