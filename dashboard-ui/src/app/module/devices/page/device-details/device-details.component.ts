import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { catchError, map, Observable, of, Subscription, switchMap } from 'rxjs';
import { User } from 'src/app/module/shared/user/models/user.model';
import { Device } from '../../models/device.model';
import { HardwareRepositoryService } from '../../services/hardware.repository.service';

@Component({
  selector: 'app-device-details',
  templateUrl: './device-details.component.html',
  styleUrls: ['./device-details.component.scss'],
})
export class DeviceDetailsComponent implements OnInit, OnDestroy {
  device$: Observable<Device> | null = null;
  deviceUserEmails$: Subscription | null = null;
  deviceError$: Observable<any> | null = null;

  emails: string[] = [];

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

    this.deviceUserEmails$ = this.route.params
      .pipe(
        switchMap((params) => {
          const deviceId = params['deviceId'];

          return this.hardwareRepository.getDeviceUsers(+deviceId);
        }),
        map((deviceUser) => deviceUser.map((user) => user.userEmail))
      )
      .subscribe((deviceEmails) => {
        this.emails = deviceEmails;
      });

    this.deviceError$ = this.device$.pipe(catchError((err) => of(err)));
  }

  addEmail(email: string) {
    this.emails = [...this.emails, email];
  }

  deleteUser(device: Device, userEmail: string): void {
    this.hardwareRepository
      .removeDeviceUser(device.code, userEmail)
      .subscribe(() => {
        this.emails = this.emails.filter((email) => email !== userEmail);
      });
  }

  ngOnDestroy(): void {
    if (this.deviceUserEmails$) {
      this.deviceUserEmails$.unsubscribe();
    }
  }
}
