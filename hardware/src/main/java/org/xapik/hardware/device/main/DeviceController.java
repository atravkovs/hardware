package org.xapik.hardware.device.main;

import java.security.Principal;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xapik.hardware.device.main.model.DeviceDTO;
import org.xapik.hardware.device.main.model.NewDeviceDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceController {

  private final DeviceService deviceService;

  @GetMapping("/list")
  @PreAuthorize("hasAuthority('admin')")
  public ResponseEntity<Page<DeviceDTO>> getDevices(@RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "5") Integer pageSize) {
    return ResponseEntity.ok(this.deviceService.getDevices(page, pageSize));
  }


  @GetMapping("")
  public ResponseEntity<List<DeviceDTO>> getUserDevices(Principal principal) {
    return ResponseEntity.ok(this.deviceService.getUserDevices(principal.getName()));
  }

  @PostMapping("")
  @PreAuthorize("hasAuthority('admin')")
  public ResponseEntity<DeviceDTO> createDevice(@Valid @RequestBody NewDeviceDTO newDeviceDTO) {
    return ResponseEntity.ok(this.deviceService.createDevice(newDeviceDTO));
  }

  @GetMapping("/{deviceCode}")
  public ResponseEntity<DeviceDTO> getDevice(@PathVariable Integer deviceCode) {
    return ResponseEntity.ok(this.deviceService.getDeviceByCode(deviceCode.longValue()));
  }

}
