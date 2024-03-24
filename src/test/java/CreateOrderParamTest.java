import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import request.OrderCancelRequest;
import request.OrderCreateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static methods.PostAPI.postCreateOrderRequest;

import static methods.PutAPI.putCancelOrder;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateOrderParamTest extends BaseTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
    private String track;


    public CreateOrderParamTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderDataTest() {
        String[] arrayBlackColor = {"BLACK"};
        String[] arrayGreyColor = {"GREY"};
        String[] arrayAllColor = {"BLACK", "GREY"};
        String[] arrayWithoutColor = {};
        return new Object[][]{
                {"Иван", "Ургант", "Пушкина, 42", "Чиркизовская", "+7-800-555-35-35", 1, "12.12.2012", "", arrayBlackColor},
                {"Петя", "Васечкин", "Пономарева, 2", "Сузаревская", "+7-800-555-35-35", 2, "12.12.2013", "Hi", arrayGreyColor},
                {"Маша", "Иванова", "Ленина, 123", "Октябрьская", "+7-800-555-35-35", 3, "12.12.2014", "-", arrayAllColor},
                {"Тест", "Тест", "Тест", "Тест", "+7-800-555-35-35", 4, "12.12.2015", "нет", arrayWithoutColor},
        };
    }

    @Test
    @DisplayName("Метод проверки создания заказа")
    @Description("Проверка кода ответа (201) и соответсвующего сообщения")
    public void checkAbilityCreateOrder() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = postCreateOrderRequest(orderCreateRequest);

        response.then()
                .assertThat()
                .statusCode(SC_CREATED);

        assertNotNull(response.then().extract().path("track"));
        track = response.then().extract().path("track").toString();
    }

   @After
    public void cancelOrder() {
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest(track);
        Response response = putCancelOrder(orderCancelRequest);

        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);
    }
}
