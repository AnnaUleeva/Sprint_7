package ru.yandex.praktikum.scooter_test.model;

public class CreateOrderResponse {
    private final String track;

    public CreateOrderResponse(String track) {
        this.track = track;
    }

    public String getTrack() {
        return track;
    }
}
