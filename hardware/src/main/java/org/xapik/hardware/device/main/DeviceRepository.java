package org.xapik.hardware.device.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xapik.hardware.device.main.model.DeviceEntity;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

}
