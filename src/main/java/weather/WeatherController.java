package weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class WeatherController {
  @Value("${app.link}")
  String link;

  @Value("${app.link2}")
  String link2;

  @GetMapping(path = "/weather")
  public List<WeatherFormatter.WeatherSeries> showWeather() {
    RestTemplate restTemplate = new RestTemplate();
    var response1 = restTemplate.getForObject(link, Coordinate.class);

    /**/ final HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "Giorgi");
    final HttpEntity<String> entity = new HttpEntity<>(headers);
    assert response1 != null;

    ResponseEntity<Weather2> response2 =
        restTemplate.exchange(
            link2 + response1.lat() + "&lon=" + response1.lon(),
            HttpMethod.GET,
            entity,
            Weather2.class);

    var weatherFormatter = new WeatherFormatter();

    Weather2 body = response2.getBody();
    assert body != null;
    body.getProperties()
        .getTimeseries()
        .forEach(
            (Weather2.Properties.Timeseries timeseries) -> {
              var weatherSeries =
                  new WeatherFormatter.WeatherSeries(
                      timeseries.getTime().toString(),
                      timeseries.getTime().toLocalDate(),
                      timeseries.getData().getInstant().getDetails().getAir_temperature(),
                      timeseries.getData().getInstant().getDetails().getRelative_humidity());
              weatherFormatter.weatherSeries.add(weatherSeries);
            });
    return weatherFormatter.weatherSeries;
  }
}
