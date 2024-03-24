import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import request.CourierCreateRequest;
import request.LoginCheckRequest;
import org.junit.Before;
import org.junit.Test;

import static constants.ErrorMessage.COURIER_NOT_FOUND;
import static constants.ErrorMessage.NOT_ENOUGH_DATA_FOR_LOGIN;
import static methods.DeleteAPI.deleteCourierRequest;
import static methods.PostAPI.postCourierCreateRequest;
import static methods.PostAPI.postCourierLoginRequest;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginCheckTest extends BaseTest {

    private LoginCheckRequest loginCheckRequest;
    private CourierCreateRequest courierCreateRequest;


    @Before
    public void courierGenerator() {
        courierCreateRequest = new CourierCreateRequest(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomNumeric(10),
                RandomStringUtils.randomAlphabetic(10)
        );

        postCourierCreateRequest(courierCreateRequest);

        loginCheckRequest = new LoginCheckRequest(courierCreateRequest.getLogin(), courierCreateRequest.getPassword());
    }

    @Test
    @DisplayName("Метод проверки авторизации курьера")
    @Description("Проверка правильного кода ответа (200) и успешный запрос возвращает id")
    public void checkAbilityLoginCourier(){
        Response response = postCourierLoginRequest(loginCheckRequest);

        response.then()
                .assertThat()
                .statusCode(SC_OK);

       assertNotNull("Не получилось авторизоваться",
               response.then().extract().path("id"));
    }

    @Test
    @DisplayName("Метод проверки авторизации под несуществующим пользователем")
    @Description("Проверка правильного кода ответа (404) и и соответствующего сообщения")
    public void checkCourierNotFound(){
        loginCheckRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        loginCheckRequest.setPassword(RandomStringUtils.randomAlphabetic(10));
        Response response = postCourierLoginRequest(loginCheckRequest);

        response.then()
                .assertThat()
                .statusCode(SC_NOT_FOUND);

        assertEquals(response.path("message"),
                COURIER_NOT_FOUND);
    }

    @Test
    @DisplayName("Метод проверки авторизации, если не указан логин")
    @Description("Проверка правильного кода ответа (400) и и соответствующего сообщения")
    public void checkCourierLoginWithoutLogin(){
        loginCheckRequest.setLogin("");
        Response response = postCourierLoginRequest(loginCheckRequest);

        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);

        assertEquals(response.path("message"),
                NOT_ENOUGH_DATA_FOR_LOGIN);
    }

    @Test
    @DisplayName("Метод проверки авторизации, если не указан пароль")
    @Description("Проверка правильного кода ответа (400) и и соответствующего сообщения")
    public void checkCourierLoginWithoutPassword(){
        loginCheckRequest.setPassword("");
        Response response = postCourierLoginRequest(loginCheckRequest);

        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);

        assertEquals(response.path("message"),
                NOT_ENOUGH_DATA_FOR_LOGIN);
    }

    @After
    public void deleteCourier() {
        if (!courierCreateRequest.getLogin().isEmpty() && !courierCreateRequest.getPassword().isEmpty()) {
            String id = postCourierLoginRequest(new LoginCheckRequest(courierCreateRequest.getLogin(), courierCreateRequest.getPassword())).path("id").toString();
            Response response = deleteCourierRequest(id);

            response.then()
                    .assertThat()
                    .statusCode(SC_OK);
        }
    }
}
