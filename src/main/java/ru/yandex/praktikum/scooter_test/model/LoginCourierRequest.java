package ru.yandex.praktikum.scooter_test.model;

public class LoginCourierRequest {
    String login;
    String password;
    public LoginCourierRequest(String login, String password){
        this.login = login;
        this.password = password;
    }
}
