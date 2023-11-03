package ru.yandex.praktikum.scooter_test.model;

public class LoginCourierResponse {
    private final Integer id;
    private final String message;

    public LoginCourierResponse(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
