package weather;

import weather.model.WeatherSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WeatherTagFormatter {
  public static Map<String, List<WeatherSeries>> createList(List<WeatherSeries> makeWeather) {
    var listOfLists = new TreeMap<String, List<WeatherSeries>>();
    makeWeather.forEach(
        (element) -> {
          var day = element.local().toLocalDate().toString();
          var listForDay = listOfLists.computeIfAbsent(day, k -> new ArrayList<>());
          listForDay.add(element);
        });

    return listOfLists;
  }
}
