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

    ResponseEntity<Weather> response2 =
        restTemplate.exchange(
            link2 + response1.lat() + "&lon=" + response1.lon(),
            HttpMethod.GET,
            entity,
            Weather.class);

    var weatherFormatter = new WeatherFormatter();

    Weather body = response2.getBody();
    assert body != null;
    body.properties()
        .timeseries()
        .forEach(
            (Weather.Properties.Timeseries timeseries) -> {
              var weatherSeries =
                  new WeatherFormatter.WeatherSeries(
                      timeseries.time(),
                      timeseries.time().toLocalDateTime(),
                      timeseries.data().instant().details().airTemperature(),
                      timeseries.data().instant().details().relativeHumidity(),
                      response1.country(),
                      response1.city());
              weatherFormatter.weatherSeries.add(weatherSeries);
            });
    return weatherFormatter.weatherSeries;
  }
}
