package org.xapik.hardware.device.device.main.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class NewDeviceDTO {

    @Getter
    @Setter
    @NotNull
    private String name;

}