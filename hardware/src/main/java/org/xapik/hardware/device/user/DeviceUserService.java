package org.xapik.hardware.device.user;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xapik.hardware.device.main.DeviceService;
import org.xapik.hardware.device.main.model.DeviceEntity;
import org.xapik.hardware.device.user.model.DeviceUserDTO;
import org.xapik.hardware.device.user.model.DeviceUserEntity;
import org.xapik.hardware.device.user.model.NewDeviceUserDTO;
import org.xapik.hardware.device.user.model.UserDeviceDoesNotExistException;
import org.xapik.hardware.device.user.model.UserDeviceExistsException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceUserService {

  private final DeviceService deviceService;
  private final DeviceUserRepository deviceUserRepository;

  public boolean isDeviceAssignedToUser(long deviceCode, String userEmail) {
    try {
      getDeviceUser(deviceCode, userEmail);

      return true;
    } catch (UserDeviceDoesNotExistException e) {
      return false;
    }
  }

  public List<DeviceUserDTO> getAssignedDevices(long deviceCode) {
    DeviceEntity device = deviceService.getDevice(deviceCode);

    return device.getDeviceUsers().stream().map(this::getDeviceUserDto).toList();
  }

  public DeviceUserDTO assignDevice(long deviceCode, NewDeviceUserDTO deviceUserDTO) {
    DeviceEntity device = deviceService.getDevice(deviceCode);
    var existing = deviceUserRepository.getDeviceUserEntityByUserEmailAndDevice(
        deviceUserDTO.getEmail(), device);

    if (existing.isPresent()) {
      throw new UserDeviceExistsException(deviceCode, deviceUserDTO.getEmail());
    }

    DeviceUserEntity deviceUser = new DeviceUserEntity();
    deviceUser.setUserEmail(deviceUserDTO.getEmail());
    deviceUser.setDevice(device);

    return getDeviceUserDto(deviceUserRepository.save(deviceUser));
  }

  public void removeDeviceAssignee(long deviceCode, String userEmail) {
    var deviceUser = getDeviceUser(deviceCode, userEmail);

    deviceUserRepository.delete(deviceUser);
  }

  private DeviceUserEntity getDeviceUser(long deviceCode, String userEmail) {
    DeviceEntity device = deviceService.getDevice(deviceCode);
    var deviceUser = deviceUserRepository.getDeviceUserEntityByUserEmailAndDevice(userEmail,
        device);

    if (deviceUser.isEmpty()) {
      throw new UserDeviceDoesNotExistException(deviceCode, userEmail);
    }

    return deviceUser.get();
  }

  private DeviceUserDTO getDeviceUserDto(DeviceUserEntity deviceUser) {
    DeviceUserDTO deviceUserDTO = new DeviceUserDTO();
    deviceUserDTO.setUserEmail(deviceUser.getUserEmail());
    deviceUserDTO.setDeviceCode(deviceUser.getDevice().getCode());

    return deviceUserDTO;
  }
}
