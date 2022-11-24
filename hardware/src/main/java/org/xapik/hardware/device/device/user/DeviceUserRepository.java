package org.xapik.hardware.device.device.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xapik.hardware.device.device.user.model.DeviceUserEntity;
import org.xapik.hardware.device.device.user.model.DeviceUserId;

public interface DeviceUserRepository extends JpaRepository<DeviceUserEntity, DeviceUserId> {
}
