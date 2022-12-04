package org.xapik.hardware.device.device.main.model;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(long deviceCode) {
        super("Device with code #" + deviceCode + " is not found");
    }

}
