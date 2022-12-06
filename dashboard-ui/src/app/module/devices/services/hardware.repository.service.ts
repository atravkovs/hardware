import { NewDevice } from './../models/new-device.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../shared/models/page.model';
import { Device } from '../models/device.model';
import { NewDeviceUser } from '../models/new-device-user.model';
import { DeviceUser } from '../models/device-user.model';

@Injectable({
  providedIn: 'root',
})
export class HardwareRepositoryService {
  constructor(private http: HttpClient) {}

  getDeviceByCode(code: number): Observable<Device> {
    return this.http.get<Device>(`/api/hardware/device/${code}`);
  }

  getDevices(): Observable<Page<Device>> {
    return this.http.get<Page<Device>>('/api/hardware/device');
  }

  createDevice(device: NewDevice): Observable<Device> {
    return this.http.post<Device>(`/api/hardware/device`, device);
  }

  assignDevice(
    deviceId: number,
    newDeviceUser: NewDeviceUser
  ): Observable<DeviceUser> {
    return this.http.post<DeviceUser>(
      `/api/hardware/device/${deviceId}/user`,
      newDeviceUser
    );
  }
}
