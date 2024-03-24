import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static methods.GetAPI.getOrderListRequest;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertNotNull;

public class OrderListCheckTest extends BaseTest {

    @Test
    @DisplayName("Метод проверки получения списка заказов")
    @Description("Проверка правильного кода ответа (200) и списка заказов")
    public void checkCourierCreate(){
        Response response = getOrderListRequest();

        response.then()
                .assertThat()
                .statusCode(SC_OK);

        assertNotNull("Не получилось получить список заказов",
                response.then().extract().path("orders"));
    }
}
