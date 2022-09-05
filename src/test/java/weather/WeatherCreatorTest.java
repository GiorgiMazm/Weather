package weather;

import org.assertj.core.api.Assertions;
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

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WeatherCreatorTest {
  @Autowired CoordinateService coordinateService;
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
  void makeWeather() {

    mockServer
        .expect(requestTo("http://ip-api.com/json/"))
        .andRespond(
            withSuccess(
                new ClassPathResource("/ip-api-response.json"), MediaType.APPLICATION_JSON));
    var currentLocation = coordinateService.getCurrentLocation();

    var weather = weatherService.getWeatherFor(coordinateService.getCurrentLocation());
    var createdWeather = WeatherCreator.makeWeather(currentLocation, weather);

    Assertions.assertThat(createdWeather.get(1).country()).isEqualTo("Germany");
    Assertions.assertThat(createdWeather.get(1).city()).isEqualTo("Hamburg");
    Assertions.assertThat(createdWeather.get(1).humidity()).isEqualTo("38.8");
    Assertions.assertThat(createdWeather.get(1).temperature()).isEqualTo("22.2");
    Assertions.assertThat(createdWeather.get(1).local()).isEqualTo("2022-09-02T10:00:00");
    Assertions.assertThat(createdWeather.get(1).utc()).isEqualTo("2022-09-02T10:00:00Z");
  }
}
