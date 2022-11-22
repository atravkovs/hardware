import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from 'src/app/module/shared/models/page.model';
import { Device } from '../../models/device.model';
import { HardwareRepositoryService } from '../../services/hardware.repository.service';

@Component({
  selector: 'app-device-list',
  templateUrl: './device-list.component.html',
  styleUrls: ['./device-list.component.scss'],
})
export class DeviceListComponent implements OnInit {
  devices$: Observable<Page<Device>> | null = null;

  constructor(private hardwareRepository: HardwareRepositoryService) {}

  ngOnInit(): void {
    this.devices$ = this.hardwareRepository.getDevices();
  }
}
