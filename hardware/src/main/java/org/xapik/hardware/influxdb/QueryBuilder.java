package org.xapik.hardware.influxdb;

import org.xapik.hardware.influxdb.model.filter.FilterEntry;
import org.xapik.hardware.influxdb.model.range.RangeEntry;

public interface QueryBuilder {

  QueryBuilder addRange(RangeEntry range);

  QueryBuilder addFilter(FilterEntry filterEntry);

  String build();


}
