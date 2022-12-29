package org.xapik.hardware.device.stats.model;

public class OrganizationNotFoundException extends RuntimeException {

  public OrganizationNotFoundException(String orgName) {
    super("Organization with name " + orgName + " is not found");
  }

}
