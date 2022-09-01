package weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import weather.model.WeatherSeries;

import java.util.List;

@RestController
public class WeatherController {
  public WeatherController(CoordinateService coordinateService, WeatherService weatherService) {
    this.coordinateService = coordinateService;
    this.weatherService = weatherService;
  }

  CoordinateService coordinateService;

  WeatherService weatherService;

  @GetMapping(path = "/weather")
  public List<WeatherSeries> showWeather() {
    var responseGeoCoordinate = coordinateService.getCurrentLocation();
    var responseWeather = weatherService.getWeatherFor(responseGeoCoordinate);
    return WeatherCreator.makeWeather(responseGeoCoordinate, responseWeather);
  }
}
