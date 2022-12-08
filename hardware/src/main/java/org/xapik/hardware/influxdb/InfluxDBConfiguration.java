package org.xapik.hardware.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InfluxDBConfiguration {

  private final InfluxDBProperties influxDBProperties;

  @Bean
  public InfluxDBClient getInfluxDbClient() {
    return InfluxDBClientFactory.create(influxDBProperties.getHost(),
        influxDBProperties.getToken().toCharArray(), influxDBProperties.getOrg());
  }

}
