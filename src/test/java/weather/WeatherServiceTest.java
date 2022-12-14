package weather;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import weather.model.Coordinate;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WeatherServiceTest {

  @Autowired WeatherService weatherService;
  private MockRestServiceServer mockServer;
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
  void request() {

    mockServer
        .expect(requestTo(startsWith("https://api.met.no/weatherapi/locationforecast/2.0/compact")))
        .andExpect(queryParam("lat", "52.520008"))
        .andExpect(queryParam("lon", "13.404954"))
        .andRespond(
            withSuccess(
                new ClassPathResource("/weather-response.json"), MediaType.APPLICATION_JSON));
  }
}
