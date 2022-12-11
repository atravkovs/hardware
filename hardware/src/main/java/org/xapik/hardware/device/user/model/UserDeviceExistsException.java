package org.xapik.hardware.device.user.model;

public class UserDeviceExistsException extends RuntimeException {

  public UserDeviceExistsException(long deviceCode, String userEmail) {
    super("Device User for device #" + deviceCode + " with " + userEmail + " email already exists");
  }

}
