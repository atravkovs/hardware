package org.xapik.hardware.device.main.model;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.xapik.hardware.device.user.model.DeviceUserEntity;

import java.util.Set;

@Data
@Entity
@Table(name = "device")
public class DeviceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long code;

  @Column
  private String name;

  @OneToMany
  @JoinColumn(name = "device_code")
  private Set<DeviceUserEntity> deviceUsers;

}
