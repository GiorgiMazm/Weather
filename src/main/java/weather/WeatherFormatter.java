package weather;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherFormatter {
  public List<WeatherSeries> weatherSeries = new ArrayList<>();
}
