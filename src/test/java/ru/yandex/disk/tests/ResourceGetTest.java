package ru.yandex.disk.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("Работа с папками")
public class ResourceGetTest extends BaseTest {
    @Test
    @DisplayName("GET: получение метаинформации о папке")
    void shouldReturnFolder() {
        given()
                .spec(spec)
                .queryParam("path", "test_folder")
                .when()
                .put("/resources");

        given()
                .spec(spec)
                .queryParam("path", "test_folder")
                .when()
                .get("/resources")
                .then()
                .statusCode(200)
                .body("type", equalTo("dir"))
                .body("name", equalTo("test_folder"));
    }

    @Test
    @DisplayName("GET: метаинформация о несуществующем ресурсе, вывод 404 ошибки")
    void shouldReturn404NonExistentCatalog() {
        given()
                .spec(spec)
                .queryParam("path", "nonexistent_folder")
                .when()
                .get("/resources")
                .then()
                .statusCode(404)
                .body("error", equalTo("DiskNotFoundError"));
    }

    @Test
    @DisplayName("GET: метаинформация без токена, вывод 401 ошибки")
    void shouldReturn401WithoutToken() {
        given()
                .baseUri("https://cloud-api.yandex.net")
                .basePath("/v1/disk")
                .queryParam("path", "test_folder")
                .when()
                .get("/resources")
                .then()
                .statusCode(401);
    }


    @AfterEach
    void cleanUp() {
        given()
                .spec(spec)
                .queryParam("path", "test_folder")
                .when()
                .delete("/resources");
    }
}
