package org.xapik.hardware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HardwareApplication {

  public static void main(String[] args) {
    SpringApplication.run(HardwareApplication.class, args);
  }

}
