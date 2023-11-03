package ru.yandex.praktikum.scooter_test.model;

public class CreateCourierResponse {
    private final Boolean ok;
    private final String message;

    public CreateCourierResponse(Boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public Boolean getOk() {
        return ok;
    }

    public String getMessage() {
        return message;
    }
}
