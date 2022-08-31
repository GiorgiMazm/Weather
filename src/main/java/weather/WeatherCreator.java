package weather;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class WeatherCreator {
  public static List<WeatherSeries> makeWeather(
      Coordinate response1, ResponseEntity<Weather> response2) {

    var weatherFormatter = new WeatherFormatter();

    Weather body = response2.getBody();
    assert body != null;
    body.properties()
        .timeseries()
        .forEach(
            (Weather.Properties.Timeseries timeseries) -> {
              var weatherSeries =
                  new WeatherSeries(
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
