package org.xapik.hardware.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xapik.hardware.device.device.main.model.DeviceEntity;
import org.xapik.hardware.device.device.main.DeviceRepository;
import org.xapik.hardware.device.device.main.model.DeviceNotFoundException;
import org.xapik.hardware.device.device.main.model.NewDeviceDTO;
import org.xapik.hardware.device.device.user.DeviceUserRepository;
import org.xapik.hardware.device.device.user.model.DeviceUserEntity;
import org.xapik.hardware.device.device.user.model.NewDeviceUserDTO;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(
            DeviceRepository deviceRepository,
            DeviceUserRepository deviceUserRepository
    ) {
        this.deviceRepository = deviceRepository;
    }

    public Page<DeviceEntity> getDevices(int pageNumber, int pageSize) {
        var pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        return this.deviceRepository.findAll(pageable);
    }

    public DeviceEntity createDevice(NewDeviceDTO newDeviceDTO) {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setName(newDeviceDTO.getName());

        return deviceRepository.save(deviceEntity);
    }

    public DeviceEntity getDevice(long deviceCode) {
        var deviceEntity = deviceRepository.findById(deviceCode);

        if (deviceEntity.isEmpty()) {
            throw new DeviceNotFoundException(deviceCode);
        }

        return deviceEntity.get();
    }

    public DeviceEntity assignDevice(long deviceCode, NewDeviceUserDTO deviceUserDTO) {
        DeviceUserEntity deviceUser = new DeviceUserEntity();
        deviceUser.setUserEmail(deviceUserDTO.getEmail());

        DeviceEntity device = getDevice(deviceCode);
        device.getDeviceUsers().add(deviceUser);

        return deviceRepository.save(device);
    }

}
