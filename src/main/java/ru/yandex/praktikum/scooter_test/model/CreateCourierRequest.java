package ru.yandex.praktikum.scooter_test.model;

public class CreateCourierRequest {
    private String login;
    private String password;
    String firstName;

    public CreateCourierRequest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CreateCourierRequest() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
