import io.restassured.RestAssured;
import org.junit.Before;

import static constants.Config.BASE_URI;

public class BaseTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }
}
