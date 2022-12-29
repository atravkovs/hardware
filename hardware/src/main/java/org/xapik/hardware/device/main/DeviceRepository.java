package org.xapik.hardware.device.main;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.xapik.hardware.device.main.model.DeviceEntity;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

  Page<DeviceEntity> findAllByNameIsLike(Pageable pageable, String name);

  @Query("SELECT d FROM DeviceEntity d INNER JOIN d.deviceUsers du WHERE du.userEmail = :userEmail")
  List<DeviceEntity> findUserDevices(@Param("userEmail") String userEmail);

}
