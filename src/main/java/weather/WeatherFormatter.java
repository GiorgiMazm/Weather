package weather;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherFormatter {
  public List<WeatherSeries> weatherSeries = new ArrayList<>();

  @Data
  public static class WeatherSeries {
    private ZonedDateTime utc;
    private LocalDateTime local;
    private float temperature;
    private float humidity;

    public WeatherSeries(
        ZonedDateTime utc, LocalDateTime local, String temperature, String humidity) {
      this.utc = utc;
      this.local = local;
      this.humidity = Float.parseFloat(humidity);
      this.temperature = Float.parseFloat(temperature);
    }
  }
}
