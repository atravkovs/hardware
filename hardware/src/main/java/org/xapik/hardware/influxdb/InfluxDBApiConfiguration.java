package org.xapik.hardware.influxdb;

import com.influxdb.client.AuthorizationsApi;
import com.influxdb.client.BucketsApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.OrganizationsApi;
import com.influxdb.client.QueryApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InfluxDBApiConfiguration {

  private final InfluxDBClient influxDBClient;

  @Bean
  public OrganizationsApi getOrganizationsApi() {
    return influxDBClient.getOrganizationsApi();
  }

  @Bean
  public BucketsApi getBucketsApi() {
    return influxDBClient.getBucketsApi();
  }

  @Bean
  public AuthorizationsApi getAuthorizationsApi() {
    return influxDBClient.getAuthorizationsApi();
  }

  @Bean
  public QueryApi getQueryApi() {
    return influxDBClient.getQueryApi();
  }

}
