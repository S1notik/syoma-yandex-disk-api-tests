package ru.yandex.disk.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static RequestSpecification spec;

    @BeforeAll
    static void setUp() {
        String token = System.getenv("YANDEX_DISK_TOKEN");
        if (token == null || token.isBlank()) {
            throw new IllegalStateException(
                    "Не задан токен. Установи переменную окружения YANDEX_DISK_TOKEN");
        }

        spec = new RequestSpecBuilder()
                .setBaseUri("https://cloud-api.yandex.net")
                .setBasePath("/v1/disk")
                .addHeader("Authorization", "OAuth " + token)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
