import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HardwareRepositoryService } from '../../../services/hardware.repository.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss'],
})
export class AddUserComponent implements OnInit {
  addUserForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
  });

  @Input()
  deviceId: number = 0;

  constructor(private hardwareRepository: HardwareRepositoryService) {}

  get email() {
    return this.addUserForm.get('email');
  }

  ngOnInit(): void {}

  addDeviceUser() {
    if (!this.addUserForm.valid) {
      return;
    }

    this.hardwareRepository
      .assignDevice(this.deviceId, {
        email: this.email?.value,
      })
      .subscribe(() => {
        this.email?.setValue('');
      });
  }
}
