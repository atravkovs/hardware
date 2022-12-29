package org.xapik.hardware.influxdb.model.filter;

import lombok.Data;

@Data
public class FluxEqualsFilter implements FilterEntry {

  private String field;
  private String value;

  public FluxEqualsFilter(String field, String value) {
    this.field = field;
    this.value = value;
  }

  @Override
  public String getQuery() {
    return "filter(fn: (r) => r[\"" + this.getField() + "\"] == \"" + this.getValue() + "\")";
  }
}
