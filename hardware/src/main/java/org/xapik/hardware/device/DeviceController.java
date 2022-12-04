package org.xapik.hardware.device;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xapik.hardware.device.device.main.model.DeviceEntity;
import org.xapik.hardware.device.device.main.model.NewDeviceDTO;
import org.xapik.hardware.device.device.user.model.NewDeviceUserDTO;

import javax.validation.Valid;

@Controller
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("")
    public ResponseEntity<Page<DeviceEntity>> getDevices(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return ResponseEntity.ok(this.deviceService.getDevices(page, pageSize));
    }

    @PostMapping("")
    public ResponseEntity<DeviceEntity> createDevice(
            @Valid @RequestBody NewDeviceDTO newDeviceDTO
    ) {
        return ResponseEntity.ok(this.deviceService.createDevice(newDeviceDTO));
    }

    @GetMapping("/{deviceCode}")
    public ResponseEntity<DeviceEntity> getDevice(
            @PathVariable Integer deviceCode
    ) {
        return ResponseEntity.ok(this.deviceService.getDevice(deviceCode.longValue()));
    }

    @PostMapping("/{deviceId}/user")
    public ResponseEntity<DeviceEntity> assignDevice(
            @PathVariable("deviceId") Integer deviceId,
            @Valid @RequestBody NewDeviceUserDTO deviceUserDTO
    ) {
        return ResponseEntity.ok(this.deviceService.assignDevice(deviceId.longValue(), deviceUserDTO));
    }

}
