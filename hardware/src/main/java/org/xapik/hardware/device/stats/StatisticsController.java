package org.xapik.hardware.device.stats;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xapik.hardware.authorization.AuthorizationService;
import org.xapik.hardware.device.stats.model.CpuPoint;
import org.xapik.hardware.device.stats.model.MemoryPoint;
import org.xapik.hardware.device.stats.model.QueryConfiguration;

@RestController
@RequestMapping("/device/{deviceCode}/statistics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsController {

  private final StatisticsService statisticsService;
  private final AuthorizationService authorizationService;

  @GetMapping("/memory")
  public ResponseEntity<List<MemoryPoint>> getMemoryStatistics(
      @PathVariable("deviceCode") Integer deviceCode, @RequestParam String from,
      @RequestParam String to, @RequestParam String field) {
    if (!authorizationService.canSeeDevice(deviceCode)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(statisticsService.getStatistics(
        new QueryConfiguration(deviceCode, "memory", field, from, to), MemoryPoint.class));
  }

  @GetMapping("/cpu")
  public ResponseEntity<List<CpuPoint>> getCpuStatistics(
      @PathVariable("deviceCode") Integer deviceCode, @RequestParam String from,
      @RequestParam String to, @RequestParam String field) {
    if (!authorizationService.canSeeDevice(deviceCode)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(statisticsService.getStatistics(
        new QueryConfiguration(deviceCode, "cpu", field, from, to), CpuPoint.class));
  }

}
