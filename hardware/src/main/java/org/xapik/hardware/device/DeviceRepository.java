package org.xapik.hardware.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xapik.hardware.device.model.DeviceEntity;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {

}
