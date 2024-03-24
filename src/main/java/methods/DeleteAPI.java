package methods;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.Config.POST_CREATE_COURIER;
import static io.restassured.RestAssured.given;

public class DeleteAPI {
    @Step("Отправка DELETE-запроса. Удаление курьера")
    public static Response deleteCourierRequest(String id) {
        return given()
                .contentType(ContentType.JSON)
                .delete(POST_CREATE_COURIER + '/' + id);
    }
}