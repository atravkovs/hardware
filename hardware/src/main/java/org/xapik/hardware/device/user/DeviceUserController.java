package org.xapik.hardware.device.user;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xapik.hardware.device.user.model.DeviceUserDTO;
import org.xapik.hardware.device.user.model.NewDeviceUserDTO;

@RestController
@RequestMapping("/device/{deviceCode}/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceUserController {

  private final DeviceUserService deviceUserService;

  @GetMapping("")
  public ResponseEntity<List<DeviceUserDTO>> getAssignedDevices(
      @PathVariable("deviceCode") Integer deviceCode) {
    return ResponseEntity.ok(this.deviceUserService.getAssignedDevices(deviceCode));
  }

  @PostMapping("")
  @PreAuthorize("hasAuthority('admin')")
  public ResponseEntity<DeviceUserDTO> assignDevice(@PathVariable("deviceCode") Integer deviceCode,
      @Valid @RequestBody NewDeviceUserDTO deviceUserDTO) {
    return ResponseEntity.ok(
        this.deviceUserService.assignDevice(deviceCode.longValue(), deviceUserDTO));
  }

  @DeleteMapping("/{userEmail}")
  @PreAuthorize("hasAuthority('admin')")
  public ResponseEntity<?> deleteUser(@PathVariable("deviceCode") Integer deviceCode,
      @PathVariable("userEmail") String userEmail) {
    this.deviceUserService.removeDeviceAssignee(deviceCode, userEmail);

    return ResponseEntity.ok().build();
  }

}
