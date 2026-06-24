package ru.yandex.disk.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@Feature("Работа с папками")
public class FolderPostTest extends BaseTest {

    @Test
    @DisplayName("POST: копирование существующего каталога")
    void shouldPostFolder() {
        given()
                .spec(spec)
                .queryParam("path", "source_folder")
                .when()
                .put("/resources");

        given()

                .spec(spec)
                .queryParam("from", "source_folder")
                .queryParam("path", "copy_folder")
                .when()
                .post("/resources/copy")
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("POST: копирование в уже существующий путь")
    void shouldReturn409DiskResourceAlreadyExists() {
        given()
                .spec(spec)
                .queryParam("path", "source_folder")
                .when()
                .put("/resources");

        given()
                .spec(spec)
                .queryParam("path", "copy_folder")
                .when()
                .put("/resources");

        given()

                .spec(spec)
                .queryParam("from", "source_folder")
                .queryParam("path", "copy_folder")
                .when()
                .post("/resources/copy")
                .then()
                .statusCode(409)
                .body("error", equalTo("DiskResourceAlreadyExistsError"));
    }

    @Test
    @DisplayName("POST: копирование несуществующего каталога, вывод 404 ошибки")
    void shouldReturn404DiskNotFoundError() {
        given()
                .spec(spec)
                .queryParam("from", "test_folder")
                .queryParam("path", "copy_folder")
                .when()
                .post("/resources/copy")
                .then()
                .statusCode(404)
                .body("error", equalTo("DiskNotFoundError"));
    }

    @Test
    @DisplayName("POST: копирование без токена, вывод 401 ошибки")
    void shouldReturn401WithoutToken() {
        given()
                .baseUri("https://cloud-api.yandex.net")
                .basePath("/v1/disk")
                .queryParam("from", "source_folder")
                .queryParam("path", "copy_folder")
                .when()
                .post("/resources/copy")
                .then()
                .statusCode(401);
    }



    @AfterEach
    void cleanUp() {
        given()
                .spec(spec)
                .queryParam("path", "source_folder")
                .when()
                .delete("/resources");

        given()
                .spec(spec)
                .queryParam("path", "copy_folder")
                .when()
                .delete("/resources");
    }
}
