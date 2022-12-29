package org.xapik.hardware.device.main;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xapik.hardware.device.main.model.DeviceDTO;
import org.xapik.hardware.device.main.model.DeviceEntity;
import org.xapik.hardware.device.main.model.DeviceNotFoundException;
import org.xapik.hardware.device.main.model.NewDeviceDTO;
import org.xapik.hardware.device.main.model.NewDeviceResponse;
import org.xapik.hardware.device.stats.StatisticsService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceService {

  private final DeviceRepository deviceRepository;
  private final StatisticsService statisticsService;

  private DeviceDTO getDeviceDto(DeviceEntity deviceEntity) {
    DeviceDTO deviceDTO = new DeviceDTO();
    deviceDTO.setCode(deviceEntity.getCode());
    deviceDTO.setName(deviceEntity.getName());

    var deviceUsers = deviceEntity.getDeviceUsers();
    if (deviceUsers == null) {
      deviceDTO.setUserCount(0);
    } else {
      deviceDTO.setUserCount(deviceUsers.size());
    }

    return deviceDTO;
  }

  public Page<DeviceDTO> getDevices(int pageNumber, int pageSize) {
    var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

    var devices = this.deviceRepository.findAll(pageable);

    return devices.map(this::getDeviceDto);
  }

  public List<DeviceDTO> getUserDevices(String userEmail) {
    var deviceEntities = deviceRepository.findUserDevices(userEmail);

    return deviceEntities.stream().map(this::getDeviceDto).toList();
  }

  public DeviceDTO createDevice(NewDeviceDTO newDeviceDTO) {
    DeviceEntity deviceEntity = new DeviceEntity();
    deviceEntity.setName(newDeviceDTO.getName());

    return getDeviceDto(deviceRepository.save(deviceEntity));
  }

  public NewDeviceResponse initialiseDevice(NewDeviceDTO newDeviceDTO) {
    var newDevice = this.createDevice(newDeviceDTO);
    this.statisticsService.createBucket("d" + newDevice.getCode());
    String token =this.statisticsService.generateToken("d2");

    NewDeviceResponse newDeviceResponse = new NewDeviceResponse();
    newDeviceResponse.setDevice(newDevice);
    newDeviceResponse.setToken(token);

    return newDeviceResponse;
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
