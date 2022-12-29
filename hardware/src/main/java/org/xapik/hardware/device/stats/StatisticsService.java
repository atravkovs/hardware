package org.xapik.hardware.device.stats;

import com.influxdb.client.AuthorizationsApi;
import com.influxdb.client.BucketsApi;
import com.influxdb.client.OrganizationsApi;
import com.influxdb.client.domain.Authorization;
import com.influxdb.client.domain.Organization;
import com.influxdb.client.domain.Permission;
import com.influxdb.client.domain.Permission.ActionEnum;
import com.influxdb.client.domain.PermissionResource;
import com.influxdb.client.domain.PermissionResource.TypeEnum;
import java.util.ArrayList;
import java.util.List;
import com.influxdb.client.QueryApi;
import lombok.RequiredArgsConstructor;
import com.influxdb.client.InfluxDBClient;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.xapik.hardware.device.stats.model.OrganizationNotFoundException;
import org.xapik.hardware.device.stats.model.QueryConfiguration;
import org.xapik.hardware.influxdb.FluxQueryBuilder;
import org.xapik.hardware.influxdb.QueryBuilder;
import org.xapik.hardware.influxdb.model.filter.FluxEqualsFilter;
import org.xapik.hardware.influxdb.model.range.FluxRange;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsService {

  private final InfluxDBClient influxDBClient;

  private final String ORGANIZATION_ID = "at20057";

  public <T> List<T> getStatistics(QueryConfiguration query, Class<T> mapTo) {
    QueryBuilder queryBuilder = new FluxQueryBuilder("d" + query.deviceCode());

    String flux = queryBuilder.addRange(new FluxRange(query.fromIsoDate(), query.toIsoDate()))
        .addFilter(new FluxEqualsFilter("_measurement", query.measurement()))
        .addFilter(new FluxEqualsFilter("_field", query.field())).build();

    QueryApi queryApi = influxDBClient.getQueryApi();

    return queryApi.query(flux, mapTo);
  }

  public Organization findOrganizationByName(String org) {
    OrganizationsApi organizationsApi = this.influxDBClient.getOrganizationsApi();

    var optional = organizationsApi.findOrganizations().stream()
        .filter(organization -> organization.getName().equals(org)).findFirst();

    if (optional.isEmpty()) {
      throw new OrganizationNotFoundException(org);
    }

    return optional.get();
  }

  public void createBucket(String bucketName) {
    BucketsApi bucketsApi = this.influxDBClient.getBucketsApi();
    var organization = this.findOrganizationByName(ORGANIZATION_ID);

    bucketsApi.createBucket(bucketName, organization.getId());
  }

  public String generateToken(String bucketName) {
    AuthorizationsApi authorizationsApi = this.influxDBClient.getAuthorizationsApi();

    var organization = this.findOrganizationByName(ORGANIZATION_ID);

    List<Permission> permissions = List.of(
        constructDeviceClientPermission(bucketName, organization));

    Authorization authorization = authorizationsApi.createAuthorization(organization.getId(), permissions);

    return authorization.getToken();
  }

  private Permission constructDeviceClientPermission(String bucketName, Organization organization) {
    Permission permission = new Permission();

    permission.setAction(ActionEnum.WRITE);
    permission.setResource(this.constructDeviceClientPermissionResource(bucketName, organization));

    return permission;
  }

  private PermissionResource constructDeviceClientPermissionResource(String bucketName,
      Organization organization) {
    PermissionResource permissionResource = new PermissionResource();

    permissionResource.setName(bucketName);
    permissionResource.setType(TypeEnum.BUCKETS);
    permissionResource.setOrgID(organization.getId());
    permissionResource.setOrg(organization.getName());

    return permissionResource;
  }

}
