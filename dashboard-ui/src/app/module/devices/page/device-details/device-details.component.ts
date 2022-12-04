import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { catchError, Observable, of, switchMap } from 'rxjs';
import { Device } from '../../models/device.model';
import { HardwareRepositoryService } from '../../services/hardware.repository.service';

@Component({
  selector: 'app-device-details',
  templateUrl: './device-details.component.html',
  styleUrls: ['./device-details.component.scss'],
})
export class DeviceDetailsComponent implements OnInit {
  device$: Observable<Device> | null = null;
  deviceError$: Observable<any> | null = null;

  constructor(
    private route: ActivatedRoute,
    private hardwareRepository: HardwareRepositoryService
  ) {}

  ngOnInit(): void {
    this.device$ = this.route.params.pipe(
      switchMap((params) => {
        const deviceId = params['deviceId'];

        return this.hardwareRepository.getDeviceByCode(+deviceId);
      })
    );

    this.deviceError$ = this.device$.pipe(catchError((err) => of(err)));
  }
}
