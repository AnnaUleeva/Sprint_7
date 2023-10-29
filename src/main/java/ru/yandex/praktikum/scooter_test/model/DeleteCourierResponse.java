package ru.yandex.praktikum.scooter_test.model;

public class DeleteCourierResponse {
    public Boolean ok;
    public String message;

    public DeleteCourierResponse(Boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }
}
