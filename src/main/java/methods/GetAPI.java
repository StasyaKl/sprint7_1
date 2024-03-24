package methods;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.Config.GET_ORDER_LIST;
import static io.restassured.RestAssured.given;

public class GetAPI {
    @Step("Отправка GET-запроса. Получение списка заказов")
    public static Response getOrderListRequest() {
        return given()
                .contentType(ContentType.JSON)
                .get(GET_ORDER_LIST);
    }

}
