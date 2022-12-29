package org.xapik.hardware.influxdb;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.xapik.hardware.influxdb.model.QueryEntry;
import org.xapik.hardware.influxdb.model.filter.FilterEntry;
import org.xapik.hardware.influxdb.model.range.RangeEntry;

public class FluxQueryBuilder implements QueryBuilder {

  @Getter
  private final String bucket;

  @Getter
  private final List<RangeEntry> ranges;

  @Getter
  private final List<FilterEntry> filters;

  public FluxQueryBuilder(String bucket) {
    this.bucket = bucket;
    this.ranges = new ArrayList<>();
    this.filters = new ArrayList<>();
  }

  @Override
  public FluxQueryBuilder addRange(RangeEntry range) {
    this.ranges.add(range);

    return this;
  }

  @Override
  public FluxQueryBuilder addFilter(FilterEntry filterEntry) {
    this.filters.add(filterEntry);

    return this;
  }

  @Override
  public String build() {
    String query = this.buildFrom();

    query += "\n" + this.buildQueries(this.getRanges());
    query += "\n" + this.buildQueries(this.getFilters());
    query += "\n |> yield(name: \"mean\")";

    return query;
  }

  private String buildQueries(List<? extends QueryEntry> queryEntries) {
    var entryList = queryEntries.stream().map(entry -> "|> " + entry.getQuery()).toList();

    return String.join("\n", entryList);
  }

  private String buildFrom() {
    return "from(bucket: \"" + this.getBucket() + "\")";
  }


}
