import config.Config;
import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommonTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Attachment(value = "Test data from json file {resourcePath}", type = "application/json", fileExtension = ".json")
    public static byte[] addAttachment(String resourcePath) throws IOException {
        return Files.readAllBytes(Paths.get(resourcePath));
    }
}
