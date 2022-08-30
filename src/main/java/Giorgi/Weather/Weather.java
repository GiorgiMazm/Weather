package Giorgi.Weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Weather {
  private Properties properties;

  @lombok.Data
  public static class Properties {
    @JsonProperty("timeseries")
    private List<Timeseries> timeseries;
  }

  @lombok.Data
  public static class Timeseries {
    private Data data;
  }

  @lombok.Data
  public static class Data {
    private Instant instant;
  }

  @lombok.Data
  public static class Instant {
    private Details details;
  }

  @lombok.Data
  public static class Details {
    private String air_temperature;
  }
}
