package weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import weather.model.Coordinate;

@Log4j2
@Service
public class CoordinateService {
  final String linkCoordinate;

  final RestTemplate restTemplate;

  public CoordinateService(RestTemplate restTemplate, @Value("${app.link}") String linkCoordinate) {
    this.linkCoordinate = linkCoordinate;
    this.restTemplate = restTemplate;
  }

  public Coordinate getCurrentLocation() {
    var responseGeoCoordinate = restTemplate.getForObject(linkCoordinate, Coordinate.class);

    log.info("response from " + linkCoordinate + responseGeoCoordinate);
    return responseGeoCoordinate;
  }
}
