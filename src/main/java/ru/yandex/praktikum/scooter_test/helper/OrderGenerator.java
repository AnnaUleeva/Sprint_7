package ru.yandex.praktikum.scooter_test.helper;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.praktikum.scooter_test.constants.OrderColors;
import ru.yandex.praktikum.scooter_test.model.CreateOrderRequest;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class OrderGenerator {
    public static CreateOrderRequest getRandomOrder(OrderColors[] color) {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        String metroStation = RandomStringUtils.randomAlphabetic(10);
        String phone = RandomStringUtils.randomAlphabetic(10);
        Integer rentTime =  ThreadLocalRandom.current().nextInt(0, 50);
        Date deliveryDate = new Date();
        String comment = RandomStringUtils.randomAlphabetic(10);

        return new CreateOrderRequest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
