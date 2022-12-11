package org.xapik.hardware.device.stats.model;

public record QueryConfiguration(long deviceCode, String measurement, String field,
                                 String fromIsoDate, String toIsoDate) {

}
