package weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public record Weather(Properties properties) {
  public record Properties(@JsonProperty("timeseries") List<Timeseries> timeseries) {
    public record Timeseries(Data data, ZonedDateTime time ) {
      public record Data(Instant instant) {
        public record Instant( Details details) {
          public record Details(@JsonProperty("air_temperature") String airTemperature, @JsonProperty("relative_humidity") String relativeHumidity ) {
          }
        }
      }
    }
  }
}