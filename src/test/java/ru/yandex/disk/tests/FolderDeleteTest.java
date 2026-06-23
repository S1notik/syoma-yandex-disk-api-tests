package ru.yandex.disk.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Feature("Работа с папками")
public class FolderDeleteTest extends BaseTest {

    @Test
    @DisplayName("DELETE: удаление существующего каталога")
    void shouldDeleteFolder() {
        given()
                .spec(spec)
                .queryParam("path", "test_folder")
                .when()
                .put("/resources");

        given()
                .spec(spec)
                .queryParam("path", "test_folder")
                .when()
                .delete("/resources")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("DELETE: удаление несуществующего каталога, вывод 404 ошибки")
    void shouldReturn404NonExistentCatalog() {
        given()
                .spec(spec)
                .queryParam("path", "test_folder")
                .when()
                .delete("/resources")
                .then()
                .statusCode(404)
                .body("error", equalTo("DiskNotFoundError"));
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
