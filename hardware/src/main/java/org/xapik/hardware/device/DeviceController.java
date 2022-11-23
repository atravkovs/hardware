package org.xapik.hardware.device;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xapik.hardware.device.model.DeviceEntity;
import org.xapik.hardware.device.model.NewDeviceDTO;

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

}
