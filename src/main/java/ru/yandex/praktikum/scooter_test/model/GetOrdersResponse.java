package ru.yandex.praktikum.scooter_test.model;

public class GetOrdersResponse {
    private final OrderListItem[] orders;

    public GetOrdersResponse(OrderListItem[] orders) {
        this.orders = orders;
    }

    public OrderListItem[] getOrders() {
        return orders;
    }
}
