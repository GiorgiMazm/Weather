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
class CoordinateServiceTest {

  @Autowired CoordinateService coordinateService;
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
        .expect(requestTo("http://ip-api.com/json/"))
        .andRespond(
            withSuccess(
                new ClassPathResource("/ip-api-response.json"), MediaType.APPLICATION_JSON));
    var currentLocation = coordinateService.getCurrentLocation();

    Assertions.assertThat(currentLocation.lat()).isEqualTo(53.5466f);
    Assertions.assertThat(currentLocation.lon()).isEqualTo(10.021f);
    Assertions.assertThat(currentLocation.city()).isEqualTo("Hamburg");
    Assertions.assertThat(currentLocation.country()).isEqualTo("Germany");
  }
}
