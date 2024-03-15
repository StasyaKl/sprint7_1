import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import request.CourierCreateRequest;
import org.junit.Test;

import static constants.ErrorMessage.LOGIN_IS_USED;
import static constants.ErrorMessage.NOT_ENOUGH_DATA_FOR_CREATE_COURIER;
import static methods.PostAPI.postCourierCreateRequest;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateCourierTest extends BaseTest {
    private CourierCreateRequest courierCreateRequest;

    @Before
    public void courierGenerator() {
        courierCreateRequest = new CourierCreateRequest(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomNumeric(10),
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    @Test
    @DisplayName("Метод проверки создания курьера")
    @Description("Проверка правильного кода ответа (201) и ok: true")
    public void checkCourierCreate() {
        Response response = postCourierCreateRequest(courierCreateRequest);

        response.then()
                .assertThat()
                .statusCode(SC_CREATED);

        assertTrue("Курьер не создан",
                response.path("ok"));
    }

    @Test
    @DisplayName("Метод проверки невозможности создания курьера без логина")
    @Description("Проверка кода ответа (400) и соответсвующего сообщения")
    public void isNotAbilityCreateCourierWithoutLogin() {
        courierCreateRequest.setLogin("");
        Response response = postCourierCreateRequest(courierCreateRequest);

        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);

        assertEquals(response.path("message"),
                NOT_ENOUGH_DATA_FOR_CREATE_COURIER);
    }

    @Test
    @DisplayName("Метод проверки невозможности создания курьера без пароля")
    @Description("Проверка кода ответа (400) и соответсвующего сообщения")
    public void isNotAbilityCreateCourierWithoutPassword() {
        courierCreateRequest.setPassword("");
        Response response = postCourierCreateRequest(courierCreateRequest);

        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);

        assertEquals(response.path("message"),
                NOT_ENOUGH_DATA_FOR_CREATE_COURIER);
    }

    @Test
    @DisplayName("Метод проверки невозможности создания пользователя с логином, который уже есть")
    @Description("Проверка кода ответа (409) и соответсвующего сообщения")
    public void isNotAbilityCreateCourierWithoutUsedLogin() {
        courierCreateRequest.setLogin("test543212");
        courierCreateRequest.setPassword(RandomStringUtils.randomNumeric(10));
        Response response = postCourierCreateRequest(courierCreateRequest);

        response.then()
                .assertThat()
                .statusCode(SC_CONFLICT);

        assertEquals(response.path("message"),
                LOGIN_IS_USED);
    }

    @Test
    @DisplayName("Метод проверки невозможности создания двух одинаковых курьеров")
    @Description("Проверка кода ответа (409) и соответсвующего сообщения")
    public void isNotAbilityCreateTheSameCourier() {
        courierCreateRequest.setLogin("test543212");
        courierCreateRequest.setPassword("6165212741");
        Response response = postCourierCreateRequest(courierCreateRequest);

        response.then()
                .assertThat()
                .statusCode(SC_CONFLICT);

        assertEquals(response.path("message"),
                LOGIN_IS_USED);
    }
}