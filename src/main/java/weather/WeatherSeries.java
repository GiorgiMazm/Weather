package weather;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record WeatherSeries(ZonedDateTime utc, LocalDateTime local, String temperature, String humidity, String country, String city ) {
  }