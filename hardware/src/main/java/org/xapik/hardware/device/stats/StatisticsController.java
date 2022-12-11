package org.xapik.hardware.device.stats;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xapik.hardware.device.stats.model.MemoryPoint;

@RestController
@RequestMapping("/device/{deviceCode}/statistics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsController {

  private final StatisticsService statisticsService;

  @GetMapping("")
  public ResponseEntity<List<MemoryPoint>> getStatistics(
      @PathVariable("deviceCode") Integer deviceCode,
      @RequestParam String from,
      @RequestParam String to
      ) {
    return ResponseEntity.ok(statisticsService.getStatistics(deviceCode, from, to));
  }

}
