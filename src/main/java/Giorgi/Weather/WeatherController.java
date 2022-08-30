package Giorgi.Weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {
  @Value("${app.link}")
  String link;

  @Value("${app.link2}")
  String link2;

  @GetMapping(path = "/weather")
  public ResponseEntity<Weather> showWeather() {
    RestTemplate restTemplate = new RestTemplate();
    var response1 = restTemplate.getForObject(link, Coordinate.class);

    /**/ final HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "eltabo");
    final HttpEntity<String> entity = new HttpEntity<String>(headers);
    assert response1 != null;

    ResponseEntity<Weather> response3 =
        restTemplate.exchange(
            link2 + response1.lat() + "&lon=" + response1.lon(),
            HttpMethod.GET,
            entity,
            Weather.class);
    System.out.println("hh" + response3);
    return response3;
  }
}
