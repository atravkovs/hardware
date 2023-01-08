package org.xapik.hardware.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.xapik.hardware.device.user.DeviceUserService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationService {

  private final DeviceUserService deviceUserService;

  /**
   * Device can be retrieved only by administrators, system functions and assigned users
   * @param deviceCode Device Code
   * @return whether it is allowed to see requested device
   */
  public boolean canSeeDevice(long deviceCode) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null) {
      return true;
    }

    if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
      return true;
    }

    User principal = (User) auth.getPrincipal();

    return deviceUserService.isDeviceAssignedToUser(deviceCode, principal.getUsername());
  }

}
