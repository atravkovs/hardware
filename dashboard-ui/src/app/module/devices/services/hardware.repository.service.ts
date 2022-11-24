import { NewDevice } from './../models/new-device.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../shared/models/page.model';
import { Device } from '../models/device.model';

@Injectable({
  providedIn: 'root',
})
export class HardwareRepositoryService {
  constructor(private http: HttpClient) {}

  getDevices(): Observable<Page<Device>> {
    return this.http.get<Page<Device>>('/api/hardware/device');
  }

  createDevice(device: NewDevice): Observable<Device> {
    return this.http.post<Device>(`/api/hardware/device`, device);
  }
}
