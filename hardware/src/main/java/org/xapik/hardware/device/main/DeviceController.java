package org.xapik.hardware.device.main;

import java.security.Principal;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xapik.hardware.authorization.AuthorizationService;
import org.xapik.hardware.device.main.model.DeviceDTO;
import org.xapik.hardware.device.main.model.NewDeviceDTO;

import javax.validation.Valid;
import org.xapik.hardware.device.main.model.NewDeviceResponse;
import org.xapik.hardware.device.stats.StatisticsService;
import org.xapik.hardware.device.user.DeviceUserService;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceController {

  private final DeviceService deviceService;
  private final AuthorizationService authorizationService;

  @GetMapping("/list")
  @PreAuthorize("hasAuthority('admin')")
  public ResponseEntity<Page<DeviceDTO>> getDevices(@RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "5") Integer pageSize,
      @RequestParam(required = false) String search) {
    return ResponseEntity.ok(this.deviceService.getDevices(page, pageSize, search));
  }


  @GetMapping("")
  public ResponseEntity<List<DeviceDTO>> getUserDevices(Principal principal) {
    return ResponseEntity.ok(this.deviceService.getUserDevices(principal.getName()));
  }

  @PostMapping("")
  @PreAuthorize("hasAuthority('admin')")
  public ResponseEntity<NewDeviceResponse> createDevice(
      @Valid @RequestBody NewDeviceDTO newDeviceDTO) {
    return ResponseEntity.ok(this.deviceService.initialiseDevice(newDeviceDTO));
  }

  @GetMapping("/{deviceCode}")
  public ResponseEntity<DeviceDTO> getDevice(@PathVariable Integer deviceCode) {
    if (!authorizationService.canSeeDevice(deviceCode)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(this.deviceService.getDeviceByCode(deviceCode.longValue()));
  }

}
