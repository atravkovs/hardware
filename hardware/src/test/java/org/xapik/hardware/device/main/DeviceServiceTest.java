package org.xapik.hardware.device.main;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.xapik.hardware.device.main.model.DeviceDTO;
import org.xapik.hardware.device.main.model.DeviceEntity;
import org.xapik.hardware.device.main.model.DeviceNotFoundException;
import org.xapik.hardware.device.main.model.NewDeviceDTO;
import org.xapik.hardware.device.main.model.NewDeviceResponse;
import org.xapik.hardware.device.stats.StatisticsService;

import static org.mockito.BDDMockito.*;

@SpringBootTest
class DeviceServiceTest {

  @MockBean
  private DeviceRepository deviceRepository;

  @MockBean
  private StatisticsService statisticsService;

  @Autowired
  private DeviceService deviceService;

  @Test
  void getDevices() {
    given(deviceRepository.findAllByNameIsLike(any(Pageable.class), anyString())).willReturn(
        Page.empty());

    var devices = deviceService.getDevices(0, 5, "test");

    assertEquals(0, devices.getSize());
  }

  @Test
  void getUserDevices() {
    given(deviceRepository.findUserDevices(anyString())).willReturn(List.of(mockDevice()));

    var devices = deviceService.getUserDevices("test@test.com");

    assertIterableEquals(List.of(mockDeviceDto()), devices);
  }

  @Test
  void createDevice() {
    given(deviceRepository.save(any(DeviceEntity.class))).willReturn(mockDevice());

    var device = deviceService.createDevice(mockNewDeviceDto());

    assertEquals(mockDeviceDto(), device);
  }

  @Test
  void initialiseDevice() {
    given(deviceRepository.save(any(DeviceEntity.class))).willReturn(mockDevice());
    given(statisticsService.generateToken(anyString())).willReturn("test123");

    var newDevice = deviceService.initialiseDevice(mockNewDeviceDto());

    assertEquals(mockNewDeviceResponse(), newDevice);
  }

  @Test
  void getDevice() {
    given(deviceRepository.findById(anyLong())).willReturn(Optional.of(mockDevice()));

    var device = deviceService.getDevice(123);

    assertEquals(mockDevice(), device);
  }
  @Test
  void getDevice_NotFound() {
    given(deviceRepository.findById(anyLong())).willReturn(Optional.empty());

    assertThrows(DeviceNotFoundException.class, () -> {
      deviceService.getDevice(123);
    });
  }

  @Test
  void getDeviceByCode() {
    given(deviceRepository.findById(anyLong())).willReturn(Optional.of(mockDevice()));

    var device = deviceService.getDeviceByCode(123);

    assertEquals(mockDeviceDto(), device);
  }

  private DeviceEntity mockDevice() {
    var device = new DeviceEntity();
    device.setDeviceUsers(Set.of());
    device.setName("Test");
    device.setCode(123);

    return device;
  }

  private DeviceDTO mockDeviceDto() {
    var deviceDto = new DeviceDTO();
    deviceDto.setName("Test");
    deviceDto.setCode(123);
    deviceDto.setUserCount(0);

    return deviceDto;
  }

  private NewDeviceResponse mockNewDeviceResponse() {
    var newDeviceResponse = new NewDeviceResponse();
    newDeviceResponse.setToken("test123");
    newDeviceResponse.setDevice(mockDeviceDto());

    return newDeviceResponse;
  }

  private NewDeviceDTO mockNewDeviceDto() {
    NewDeviceDTO newDeviceDTO = new NewDeviceDTO();
    newDeviceDTO.setName("Test");

    return newDeviceDTO;
  }
}