package org.xapik.hardware.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xapik.hardware.device.model.DeviceEntity;

@Service
public class DeviceService {

  private final DeviceRepository deviceRepository;

  @Autowired
  public DeviceService(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  public Page<DeviceEntity> getDevices(int pageNumber, int pageSize) {
    var pageable = Pageable
        .ofSize(pageSize)
        .withPage(pageNumber);

    return this.deviceRepository.findAll(pageable);
  }

}
