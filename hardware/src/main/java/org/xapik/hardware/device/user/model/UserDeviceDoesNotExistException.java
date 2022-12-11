package org.xapik.hardware.device.user.model;

public class UserDeviceDoesNotExistException extends RuntimeException {

  public UserDeviceDoesNotExistException(long deviceCode, String userEmail) {
    super("Device User with for device #" + deviceCode + " with " + userEmail
        + " email does not exist");
  }

}
