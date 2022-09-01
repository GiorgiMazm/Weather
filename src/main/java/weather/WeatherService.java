package weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import weather.model.Coordinate;
import weather.model.Weather;

@Log4j2
@Service
public class WeatherService {
  private final RestTemplate restTemplate;
  private final String linkWeather;

  public WeatherService(RestTemplate restTemplate, @Value("${app.link2}") String linkWeather) {
    this.restTemplate = restTemplate;
    this.linkWeather = linkWeather;
  }

  public Weather getWeatherFor(Coordinate location) {

    final HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "Giorgi");
    final HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<Weather> responseWeather =
        restTemplate.exchange(
            linkWeather + location.lat() + "&lon=" + location.lon(),
            HttpMethod.GET,
            entity,
            Weather.class);

    log.info("response from " + linkWeather + responseWeather);
    return responseWeather.getBody();
  }
}
