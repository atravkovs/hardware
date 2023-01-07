package org.xapik.hardware.influxdb;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.xapik.hardware.influxdb.model.IQueryEntry;
import org.xapik.hardware.influxdb.model.filter.IFilterEntry;
import org.xapik.hardware.influxdb.model.range.IRangeEntry;

/**
 * Builder to dynamically generate Flux queries to InfluxDB
 */
public class FluxQueryBuilder implements IQueryBuilder {
  @Getter
  private final String bucket;
  @Getter
  private final List<IRangeEntry> ranges;
  @Getter
  private final List<IFilterEntry> filters;

  public FluxQueryBuilder(String bucket) {
    this.bucket = bucket;
    this.ranges = new ArrayList<>();
    this.filters = new ArrayList<>();
  }

  @Override
  public FluxQueryBuilder addRange(IRangeEntry range) {
    this.ranges.add(range);

    return this;
  }

  @Override
  public FluxQueryBuilder addFilter(IFilterEntry filterEntry) {
    this.filters.add(filterEntry);

    return this;
  }

  /**
   * Converts builder parameters to Flux Query
   *
   * @return InfluxDB Flux Query
   */
  @Override
  public String build() {
    String query = this.buildFrom();

    query += "\n" + this.buildQueries(this.getRanges());
    query += "\n" + this.buildQueries(this.getFilters());
    query += "\n |> yield(name: \"mean\")";

    return query;
  }

  private String buildQueries(List<? extends IQueryEntry> queryEntries) {
    var entryList = queryEntries.stream().map(entry -> "|> " + entry.getQuery()).toList();

    return String.join("\n", entryList);
  }

  private String buildFrom() {
    return "from(bucket: \"" + this.getBucket() + "\")";
  }
}
