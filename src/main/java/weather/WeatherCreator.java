package weather;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class WeatherCreator {
  public static List<WeatherSeries> makeWeather(
      Coordinate responseGeoCoordinate, ResponseEntity<Weather> responseWeather) {

    var weatherFormatter = new WeatherFormatter();

    Weather body = responseWeather.getBody();
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
                      responseGeoCoordinate.country(),
                      responseGeoCoordinate.city());
              weatherFormatter.weatherSeries.add(weatherSeries);
            });

    return weatherFormatter.weatherSeries;
  }
}
