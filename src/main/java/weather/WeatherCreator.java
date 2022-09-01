package weather;

import weather.model.Coordinate;
import weather.model.Weather;
import weather.model.WeatherFormatter;
import weather.model.WeatherSeries;

import java.util.List;

public class WeatherCreator {
  public static List<WeatherSeries> makeWeather(
      Coordinate responseGeoCoordinate, Weather responseWeather) {

    var weatherFormatter = new WeatherFormatter();
    assert responseWeather != null;
    responseWeather
        .properties()
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
