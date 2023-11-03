package ru.yandex.praktikum.scooter_test.model;

import ru.yandex.praktikum.scooter_test.constants.OrderColors;

import java.util.Date;

public class CreateOrderRequest {
    String firstName;
    String lastName;
    String address;
    String metroStation;
    String phone;
    Integer rentTime;
    Date deliveryDate;
    String comment;
    OrderColors[] color;

    public CreateOrderRequest(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, Date deliveryDate, String comment, OrderColors[] color) {
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


