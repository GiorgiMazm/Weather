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
import weather.model.Coordinate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WeatherControllerTest {

  @Autowired WeatherService weatherService;
  private MockRestServiceServer mockServer;
  @Autowired private RestTemplate restTemplate;

  Coordinate coordinate = new Coordinate(50f, 60f, "de", "Hamburg");

  @BeforeEach
  public void createMockServer() {
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
  }

  @AfterEach
  public void cleanMockServer() {
    mockServer.reset();
  }

  @Test
  void callsApiReturnsResult() {
    mockServer
        .expect(requestTo("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=50.0&lon=60.0"))
        .andRespond(
            withSuccess(
                new ClassPathResource("/weather-response.json"), MediaType.APPLICATION_JSON));

                var currentLocation = coordinate;
                var weather = weatherService.getWeatherFor(coordinate);

    var createdWeather = WeatherCreator.makeWeather(currentLocation, weather);

    Assertions.assertThat(createdWeather.get(1).country()).isEqualTo("de");
    Assertions.assertThat(createdWeather.get(1).city()).isEqualTo("Hamburg");
    Assertions.assertThat(createdWeather.get(1).humidity()).isEqualTo("46.4");
    Assertions.assertThat(createdWeather.get(1).temperature()).isEqualTo("20.3");
    Assertions.assertThat(createdWeather.get(1).local()).isEqualTo("2022-09-01T12:00");
    Assertions.assertThat(createdWeather.get(1).utc()).isEqualTo("2022-09-01T12:00Z");
  }
}