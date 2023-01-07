package org.xapik.hardware.influxdb.model.range;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class FluxRange implements IRangeEntry {

  private String from;
  private String to;

  public FluxRange(String from, String to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public String getQuery() {
    return "range(" + getQueryComponents() + ")";
  }

  private String getQueryComponents() {
    List<String> queryComponents = new ArrayList<>();

    if (this.getFrom() != null) {
      queryComponents.add("start: " + this.getFrom());
    }

    if (this.getTo() != null) {
      queryComponents.add("stop: " + this.getTo());
    }

    return String.join(", ", queryComponents);
  }
}
