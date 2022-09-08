package weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public record Weather(Properties properties) {
  public record Properties(@JsonProperty("timeseries") List<Timeseries> timeseries) {
    public record Timeseries(Data data, ZonedDateTime time) {

      public record Data(Instant instant, Next_1_hours next_1_hours, Next_6_hours next_6_hours) {
        public record Next_1_hours(Summary summary) {
        public record Summary(@JsonProperty("symbol_code") String symbolCode) {

        }
      }

      public record Next_6_hours(Summary summary) {
        public record Summary(@JsonProperty("symbol_code") String symbolCode) {

        }
      }
        public record Instant( Details details) {
          public record Details(@JsonProperty("air_temperature") String airTemperature, @JsonProperty("relative_humidity") String relativeHumidity ) {
          }
        }
      }
    }
  }
}
