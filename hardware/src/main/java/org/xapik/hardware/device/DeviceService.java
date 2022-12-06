package org.xapik.hardware.device;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xapik.hardware.device.device.main.model.DeviceDTO;
import org.xapik.hardware.device.device.main.model.DeviceEntity;
import org.xapik.hardware.device.device.main.DeviceRepository;
import org.xapik.hardware.device.device.main.model.DeviceNotFoundException;
import org.xapik.hardware.device.device.main.model.NewDeviceDTO;
import org.xapik.hardware.device.device.user.DeviceUserRepository;
import org.xapik.hardware.device.device.user.model.DeviceUserDTO;
import org.xapik.hardware.device.device.user.model.DeviceUserEntity;
import org.xapik.hardware.device.device.user.model.NewDeviceUserDTO;
import org.xapik.hardware.device.device.user.model.UserDeviceExistsException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceService {

  private final DeviceRepository deviceRepository;
  private final DeviceUserRepository deviceUserRepository;

  private DeviceDTO getDeviceDto(DeviceEntity deviceEntity) {
    DeviceDTO deviceDTO = new DeviceDTO();
    deviceDTO.setCode(deviceEntity.getCode());
    deviceDTO.setName(deviceEntity.getName());
    deviceDTO.setUserCount(deviceEntity.getDeviceUsers().size());

    return deviceDTO;
  }

  private DeviceUserDTO getDeviceUserDto(DeviceUserEntity deviceUser) {
    DeviceUserDTO deviceUserDTO = new DeviceUserDTO();
    deviceUserDTO.setUserEmail(deviceUser.getUserEmail());
    deviceUserDTO.setDeviceCode(deviceUser.getDevice().getCode());

    return deviceUserDTO;
  }

  public Page<DeviceDTO> getDevices(int pageNumber, int pageSize) {
    var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

    var devices = this.deviceRepository.findAll(pageable);

    return devices.map(this::getDeviceDto);
  }

  public DeviceDTO createDevice(NewDeviceDTO newDeviceDTO) {
    DeviceEntity deviceEntity = new DeviceEntity();
    deviceEntity.setName(newDeviceDTO.getName());

    return getDeviceDto(deviceRepository.save(deviceEntity));
  }

  private DeviceEntity getDevice(long deviceCode) {
    var deviceEntity = deviceRepository.findById(deviceCode);

    if (deviceEntity.isEmpty()) {
      throw new DeviceNotFoundException(deviceCode);
    }

    return deviceEntity.get();
  }

  public DeviceDTO getDeviceByCode(long deviceCode) {
    return getDeviceDto(getDevice(deviceCode));
  }

  public List<DeviceUserDTO> getAssignedDevices(long deviceCode) {
    DeviceEntity device = getDevice(deviceCode);

    return device.getDeviceUsers().stream().map(this::getDeviceUserDto).toList();
  }

  public DeviceUserDTO assignDevice(long deviceCode, NewDeviceUserDTO deviceUserDTO) {
    DeviceEntity device = getDevice(deviceCode);
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

}
