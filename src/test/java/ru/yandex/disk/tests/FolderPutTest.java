package ru.yandex.disk.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@Feature("Работа с папками")
public class FolderPutTest extends BaseTest {
    @Test
    @DisplayName("PUT: создание каталога")
    void shouldCreateFolder() {
        given()
                .spec(spec)
                .queryParam("path", "test_folder")
                .when()
                .put("/resources")
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("PUT: создание папки, которая уже существует, вывод 409 ошибки")
    void shouldReturn409WhenFolderExists() {

        given()
                .spec(spec)
                .queryParam("path", "test_folder")
                .when()
                .put("/resources");

        given()
                .spec(spec)
                .queryParam("path", "test_folder")
                .when()
                .put("/resources")
                .then()
                .statusCode(409)
                .body("error", equalTo("DiskPathPointsToExistentDirectoryError"));
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