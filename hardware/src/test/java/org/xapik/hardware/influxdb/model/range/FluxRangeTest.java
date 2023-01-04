package org.xapik.hardware.influxdb.model.range;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FluxRangeTest {

  @Test
  void getQuery() {
    var range = new FluxRange("2022-10-04T21:25:45.023Z", "2022-10-05T21:25:45.023Z");

    assertEquals("range(start: 2022-10-04T21:25:45.023Z, stop: 2022-10-05T21:25:45.023Z)",
        range.getQuery());
  }

  @Test
  void getQuery_OnlyFrom() {
    var range = new FluxRange("2022-10-04T21:25:45.023Z", null);

    assertEquals("range(start: 2022-10-04T21:25:45.023Z)", range.getQuery());
  }

  @Test
  void getQuery_OnlyTo() {
    var range = new FluxRange(null, "2022-10-05T21:25:45.023Z");

    assertEquals("range(stop: 2022-10-05T21:25:45.023Z)", range.getQuery());
  }
}