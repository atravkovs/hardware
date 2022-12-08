package org.xapik.hardware.device.main;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xapik.hardware.device.main.model.DeviceDTO;
import org.xapik.hardware.device.main.model.DeviceEntity;
import org.xapik.hardware.device.main.DeviceRepository;
import org.xapik.hardware.device.main.model.DeviceNotFoundException;
import org.xapik.hardware.device.main.model.NewDeviceDTO;
import org.xapik.hardware.device.user.model.DeviceUserDTO;
import org.xapik.hardware.device.user.model.DeviceUserEntity;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceService {

  private final DeviceRepository deviceRepository;

  private DeviceDTO getDeviceDto(DeviceEntity deviceEntity) {
    DeviceDTO deviceDTO = new DeviceDTO();
    deviceDTO.setCode(deviceEntity.getCode());
    deviceDTO.setName(deviceEntity.getName());
    deviceDTO.setUserCount(deviceEntity.getDeviceUsers().size());

    return deviceDTO;
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

  public DeviceEntity getDevice(long deviceCode) {
    var deviceEntity = deviceRepository.findById(deviceCode);

    if (deviceEntity.isEmpty()) {
      throw new DeviceNotFoundException(deviceCode);
    }

    return deviceEntity.get();
  }

  public DeviceDTO getDeviceByCode(long deviceCode) {
    return getDeviceDto(getDevice(deviceCode));
  }

}
