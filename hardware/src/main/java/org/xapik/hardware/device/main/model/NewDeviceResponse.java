package org.xapik.hardware.device.main.model;

import lombok.Data;

@Data
public class NewDeviceResponse {

  private DeviceDTO device;
  private String token;

}
