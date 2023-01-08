package org.xapik.hardware.device.stats;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.influxdb.client.AuthorizationsApi;
import com.influxdb.client.BucketsApi;
import com.influxdb.client.OrganizationsApi;
import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.Authorization;
import com.influxdb.client.domain.Organization;
import com.influxdb.client.domain.Organization.StatusEnum;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.xapik.hardware.device.stats.model.MemoryPoint;
import org.xapik.hardware.device.stats.model.OrganizationNotFoundException;
import org.xapik.hardware.device.stats.model.QueryConfiguration;


@SpringBootTest
class StatisticsServiceTest {

  @MockBean
  private QueryApi queryApi;
  @MockBean
  private BucketsApi bucketsApi;
  @MockBean
  private OrganizationsApi organizationsApi;
  @MockBean
  private AuthorizationsApi authorizationsApi;

  @Autowired
  private StatisticsService statisticsService;

  @Test
  void getStatistics() {
    given(queryApi.query(anyString(), eq(MemoryPoint.class))).willReturn(List.of());

    var list = statisticsService.getStatistics(
        new QueryConfiguration(1, "test", "test", "test", "test"), MemoryPoint.class);

    assertEquals(0, list.size());
  }

  @Test
  void findOrganizationByName_NotFound() {
    given(organizationsApi.findOrganizations()).willReturn(
        List.of(mockOrganization("test"), mockOrganization("at20057")));

    assertThrows(OrganizationNotFoundException.class, () -> {
        statisticsService.findOrganizationByName("asdf");
    });
  }
  @Test
  void findOrganizationByName_Found() {
    given(organizationsApi.findOrganizations()).willReturn(
        List.of(mockOrganization("test"), mockOrganization("at20057")));

    var org = statisticsService.findOrganizationByName("at20057");

    assertEquals(mockOrganization("at20057"), org);
  }

  @Test
  void createBucket() {
    given(organizationsApi.findOrganizations()).willReturn(
        List.of(mockOrganization("test"), mockOrganization("at20057")));

    statisticsService.createBucket("test123");

    verify(bucketsApi, times(1)).createBucket("test123", (String) null);
  }

  private Organization mockOrganization(String name) {
    Organization organization = new Organization();
    organization.setName(name);
    organization.setDescription("TEst");
    organization.setStatus(StatusEnum.ACTIVE);

    return organization;
  }

}