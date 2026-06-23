package ru.yandex.disk.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Информация о Диске")
public class DiskInfoGetTest extends BaseTest {
    @Test
    @DisplayName("GET: получение информации о диске")
    @Description("Запрос GET /v1/disk возвращает корректные данные: общий и занятый объём")
    void shouldReturnDiskInfo() {
        given()
                .spec(spec)
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("total_space", notNullValue())
                .body("used_space", notNullValue())
                .body("total_space", greaterThan(0L));
    }
}
