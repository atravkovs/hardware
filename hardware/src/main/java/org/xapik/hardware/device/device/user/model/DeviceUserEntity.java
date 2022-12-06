package org.xapik.hardware.device.device.user.model;

import lombok.Getter;
import lombok.Setter;
import org.xapik.hardware.device.device.main.model.DeviceEntity;

import javax.persistence.*;

@Entity
@Table(name = "device_user")
public class DeviceUserEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_code", nullable = false)
    private DeviceEntity device;

    @Getter
    @Setter
    @Column
    private String userEmail;

}

