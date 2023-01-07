package org.xapik.hardware.influxdb;

import org.xapik.hardware.influxdb.model.filter.IFilterEntry;
import org.xapik.hardware.influxdb.model.range.IRangeEntry;

public interface IQueryBuilder {

  IQueryBuilder addRange(IRangeEntry range);

  IQueryBuilder addFilter(IFilterEntry filterEntry);

  String build();


}
