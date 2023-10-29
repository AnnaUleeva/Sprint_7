package ru.yandex.praktikum.scooter_test.model;

import java.util.Date;

public class CreateOrderRequest {
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public Integer rentTime;
    public Date deliveryDate;
    public String comment;
    public String[] color;

    public CreateOrderRequest(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, Date deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
}


