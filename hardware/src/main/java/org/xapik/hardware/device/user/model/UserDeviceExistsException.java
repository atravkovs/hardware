package org.xapik.hardware.device.user.model;

public class UserDeviceExistsException extends RuntimeException {

  public UserDeviceExistsException(long deviceCode, String userEmail) {
    super("Device with code #" + deviceCode + " and " + userEmail + " email already exists");
  }

}
