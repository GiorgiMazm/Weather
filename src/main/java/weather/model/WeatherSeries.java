package weather.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public record WeatherSeries(ZonedDateTime utc, LocalDateTime local, String temperature, String humidity, String country, String city, String symbolCode ) {
  public LocalTime getTime() {
    return local.toLocalTime();
  }
  }