package org.xapik.hardware.device.main.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DeviceDTO {

  private long code;

  private String name;

  private long userCount;

}
