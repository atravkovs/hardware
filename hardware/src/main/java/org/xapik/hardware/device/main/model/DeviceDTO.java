package org.xapik.hardware.device.main.model;

import lombok.Getter;
import lombok.Setter;

public class DeviceDTO {

  @Getter
  @Setter
  private long code;

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private long userCount;

}
