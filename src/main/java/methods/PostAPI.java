package methods;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import request.CourierCreateRequest;
import request.LoginCheckRequest;
import request.OrderCreateRequest;

import static constants.Config.*;
import static io.restassured.RestAssured.given;

public class PostAPI {
    @Step("Отправка POST-запроса. Создание курьера")
    public static Response postCourierCreateRequest(CourierCreateRequest courierCreateRequest) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courierCreateRequest)
                .when()
                .post(POST_CREATE_COURIER);
    }

    @Step("Отправка POST-запроса. Логин курьера в системе")
    public static Response postCourierLoginRequest(LoginCheckRequest loginCheckRequest) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(loginCheckRequest)
                .when()
                .post(POST_LOGIN_COURIER);
    }

    @Step("Отправка POST-запроса. Создание заказа")
    public static Response postCreateOrderRequest(OrderCreateRequest orderCreateRequest) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(orderCreateRequest)
                .when()
                .post(POST_ORDER_COURIER);
    }
}
