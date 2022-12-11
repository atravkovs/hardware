package org.xapik.hardware.device.stats;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xapik.hardware.device.stats.model.MemoryPoint;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsService {

  private final InfluxDBClient influxDBClient;

  public List<MemoryPoint> getStatistics(long deviceCode, String fromIsoDate, String toIsoDate) {
    String flux = String.format("""
        from(bucket: "d%d")
          |> range(start: %s, stop: %s)
          |> filter(fn: (r) => r["_measurement"] == "memory")
          |> filter(fn: (r) => r["_field"] == "used")
          |> yield(name: "mean")
          """, deviceCode, fromIsoDate, toIsoDate);

    QueryApi queryApi = influxDBClient.getQueryApi();

    return queryApi.query(flux, MemoryPoint.class);
  }

}
