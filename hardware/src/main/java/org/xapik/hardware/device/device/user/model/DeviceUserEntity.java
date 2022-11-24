package org.xapik.hardware.device.device.user.model;

import lombok.Getter;
import lombok.Setter;
import org.xapik.hardware.device.device.main.model.DeviceEntity;

import javax.persistence.*;

@Entity
@IdClass(DeviceUserId.class)
@Table(name = "device_user")
public class DeviceUserEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "device_code", nullable = false)
    private DeviceEntity device;

    @Id
    @Getter
    @Setter
    @Column
    private String userEmail;

}

