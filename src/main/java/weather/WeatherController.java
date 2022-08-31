package weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@RestController
public class WeatherController {
  @Value("${app.link}")
  String linkCoordinate;

  @Value("${app.link2}")
  String linkWeather;

  @GetMapping(path = "/weather")
  public List<WeatherSeries> showWeather() {
    RestTemplate restTemplate = new RestTemplate();
    var responseGeoCoordinate = restTemplate.getForObject(linkCoordinate, Coordinate.class);
    assert responseGeoCoordinate != null;
    log.info("response from " + linkCoordinate + responseGeoCoordinate);

    final HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "Giorgi");
    final HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<Weather> responseWeather =
        restTemplate.exchange(
            linkWeather + responseGeoCoordinate.lat() + "&lon=" + responseGeoCoordinate.lon(),
            HttpMethod.GET,
            entity,
            Weather.class);

    log.info("response from " + linkWeather + responseWeather);
    return WeatherCreator.makeWeather(responseGeoCoordinate, responseWeather);
  }
}
