package weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Coordinate(float lat, float lon, String country, String city, @JsonProperty("timezone") String timeZone ){
}
