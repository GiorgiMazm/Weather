package weather;

import weather.model.Coordinate;
import weather.model.Weather;
import weather.model.WeatherFormatter;
import weather.model.WeatherSeries;

import java.time.ZoneId;
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
              String symbolCode;

              if (timeseries.data().next_6_hours() == null
                  && timeseries.data().next_1_hours() == null) {
                symbolCode = "cloudy";
              } else if (timeseries.data().next_1_hours() == null) {
                symbolCode = timeseries.data().next_6_hours().summary().symbolCode();
              } else {
                symbolCode = timeseries.data().next_1_hours().summary().symbolCode();
              }

              var weatherSeries =
                  new WeatherSeries(
                      timeseries.time(),
                      timeseries
                          .time()
                          .withZoneSameInstant(ZoneId.of(responseGeoCoordinate.timeZone()))
                          .toLocalDateTime(),
                      timeseries.data().instant().details().airTemperature(),
                      timeseries.data().instant().details().relativeHumidity(),
                      responseGeoCoordinate.country(),
                      responseGeoCoordinate.city(),
                      symbolCode);
              weatherFormatter.weatherSeries.add(weatherSeries);
            });

    return weatherFormatter.weatherSeries;
  }
}
