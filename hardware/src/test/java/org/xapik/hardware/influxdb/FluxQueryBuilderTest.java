package org.xapik.hardware.influxdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.xapik.hardware.influxdb.model.filter.FluxEqualsFilter;
import org.xapik.hardware.influxdb.model.range.FluxRange;

class FluxQueryBuilderTest {

  @Test
  void addRange() {
    var builder = new FluxQueryBuilder("test");
    builder.addRange(new FluxRange(null, null));

    assertEquals(1,  builder.getRanges().size());
  }

  @Test
  void addFilter() {
    var builder = new FluxQueryBuilder("test");
    builder.addFilter(new FluxEqualsFilter("test", "test"));

    assertEquals(1,  builder.getFilters().size());
  }

  @Test
  void build() {
    var builder = new FluxQueryBuilder("test");
    var query = builder.build();

    assertTrue(query.contains("from(bucket: \"test\")"));
    assertTrue(query.contains("|> yield(name: \"mean\")"));
  }
}