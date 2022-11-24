package org.xapik.hardware.device.device.main.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.xapik.hardware.device.device.user.model.DeviceUserEntity;

import java.util.Set;

@Entity
@Table(name = "device")
public class DeviceEntity {

  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long code;

  @Getter
  @Setter
  @Column
  private String name;

  @Getter
  @Setter
  @OneToMany
  @JoinColumn(name = "device_code")
  private Set<DeviceUserEntity> deviceUsers;

}
