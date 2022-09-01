package weather;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WeatherControllerTest {

  private MockRestServiceServer mockServer;
  @Autowired private MockMvc mockMvc;
  @Autowired private RestTemplate restTemplate;

  @BeforeEach
  public void createMockServer() {
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
  }

  @AfterEach
  public void cleanMockServer() {
    mockServer.reset();
  }

  @Test
  void callsApiReturnsResult() throws Exception {}
}
