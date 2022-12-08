package org.xapik.hardware.influxdb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "influxdb")
@RequiredArgsConstructor
public class InfluxDBProperties {

  @Setter
  @Getter
  private String host;
  @Setter
  @Getter
  private String token;
  @Setter
  @Getter
  private String org;

}
