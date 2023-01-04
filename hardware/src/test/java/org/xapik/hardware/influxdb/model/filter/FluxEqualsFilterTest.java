package org.xapik.hardware.influxdb.model.filter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FluxEqualsFilterTest {

  @Test
  void getQuery() {
    var filter = new FluxEqualsFilter("field", "value");

    assertEquals("filter(fn: (r) => r[\"field\"] == \"value\")", filter.getQuery());
  }
}