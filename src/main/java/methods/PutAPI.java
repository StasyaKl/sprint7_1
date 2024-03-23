package methods;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import request.OrderCancelRequest;

import static constants.Config.PUT_CANCEL_ORDER;
import static io.restassured.RestAssured.given;

public class PutAPI {
    @Step("Отправка PUT-запроса. Отмена заказа")
    public static Response putCancelOrder(OrderCancelRequest orderCancelRequest) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(orderCancelRequest)
                .when()
                .put(PUT_CANCEL_ORDER);
    }
}
