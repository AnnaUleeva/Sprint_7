package ru.yandex.praktikum.scooter_test.model;

public class CreateCourierRequest {
    public String login;
    public String password;
    public String firstName;

    public CreateCourierRequest(String login, String password, String firstName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public CreateCourierRequest(){

    }
}
