package ru.yandex.praktikum.scooter_test.model;

public class GetOrdersResponse {
    public OrderListItem[] orders;

    public GetOrdersResponse(OrderListItem[] orders){
        this.orders = orders;
    }
}
