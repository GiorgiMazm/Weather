package weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import weather.CoordinateService;
import weather.WeatherCreator;
import weather.WeatherService;
import weather.WeatherTagFormatter;

@Controller
public class WeatherController {
  CoordinateService coordinateService;
  WeatherService weatherService;

  public WeatherController(CoordinateService coordinateService, WeatherService weatherService) {
    this.coordinateService = coordinateService;
    this.weatherService = weatherService;
  }

  @GetMapping(path = "/weather")
  public String showWeather(Model model) {
    var responseGeoCoordinate = coordinateService.getCurrentLocation();
    var responseWeather = weatherService.getWeatherFor(responseGeoCoordinate);
    var result = WeatherCreator.makeWeather(responseGeoCoordinate, responseWeather);
    var weatherByDay = WeatherTagFormatter.createList(result);

    model.addAttribute("city", result.get(0).city());
    model.addAttribute("country", result.get(0).country());
    model.addAttribute("weatherDays", weatherByDay.entrySet());

    return "weather";
  }
}
