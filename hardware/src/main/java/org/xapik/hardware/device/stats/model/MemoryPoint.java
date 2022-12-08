package org.xapik.hardware.device.stats.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Measurement(name = "memory")
public class MemoryPoint {

  @Getter
  @Setter
  @Column(name = "time")
  private Instant time;

  @Getter
  @Setter
  @Column(name = "_value")
  private Long value;

}
