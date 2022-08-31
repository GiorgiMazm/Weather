package weather;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherFormatter {
  public List<WeatherSeries> weatherSeries = new ArrayList<>();

  @Data
  public static class WeatherSeries {
    private String utc;
    private String local;
    private float temperature;
    private float humidity;

    public WeatherSeries(String utc, LocalDate local, String temperature, String humidity) {
      this.utc = utc;
      this.local = String.valueOf(local);
      this.humidity = Float.parseFloat(humidity);
      this.temperature = Float.parseFloat(temperature);
    }
  }
}
