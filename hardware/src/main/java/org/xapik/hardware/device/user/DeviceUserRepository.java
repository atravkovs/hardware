package org.xapik.hardware.device.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.xapik.hardware.device.main.model.DeviceEntity;
import org.xapik.hardware.device.user.model.DeviceUserEntity;

public interface DeviceUserRepository extends JpaRepository<DeviceUserEntity, Long> {

  List<DeviceUserEntity> findByDeviceCode(Long deviceCode);

  Optional<DeviceUserEntity> getDeviceUserEntityByUserEmailAndDevice(String userEmail,
      DeviceEntity device);

}
