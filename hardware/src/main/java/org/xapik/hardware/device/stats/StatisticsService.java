package org.xapik.hardware.device.stats;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xapik.hardware.device.stats.model.MemoryPoint;
import org.xapik.hardware.device.stats.model.QueryConfiguration;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsService {

  private final InfluxDBClient influxDBClient;

  public <T> List<T> getStatistics(QueryConfiguration query, Class<T> mapTo) {
    String flux = String.format("""
            from(bucket: "d%d")
              |> range(start: %s, stop: %s)
              |> filter(fn: (r) => r["_measurement"] == "%s")
              |> filter(fn: (r) => r["_field"] == "%s")
              |> yield(name: "mean")
              """, query.deviceCode(), query.fromIsoDate(), query.toIsoDate(), query.measurement(),
        query.field());

    QueryApi queryApi = influxDBClient.getQueryApi();

    return queryApi.query(flux, mapTo);
  }

}
