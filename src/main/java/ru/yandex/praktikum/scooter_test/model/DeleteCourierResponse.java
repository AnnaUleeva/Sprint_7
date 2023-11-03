package ru.yandex.praktikum.scooter_test.model;

public class DeleteCourierResponse {
    private final Boolean ok;
    String message;

    public DeleteCourierResponse(Boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public Boolean getOk() {
        return ok;
    }
}
