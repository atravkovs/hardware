import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeviceListComponent } from './page/device-list/device-list.component';
import { WrappersModule } from '../shared/wrappers/wrappers.module';
import { RouterModule, Routes } from '@angular/router';
import { CreateDeviceComponent } from './page/create-device/create-device.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DeviceDetailsComponent } from './page/device-details/device-details.component';
import { AddUserComponent } from './page/device-details/add-user/add-user.component';
import { UserModule } from '../shared/user/user.module';

const routes: Routes = [
  {
    path: '',
    component: DeviceListComponent,
  },
  {
    path: 'create',
    component: CreateDeviceComponent,
  },
  {
    path: ':deviceId',
    component: DeviceDetailsComponent,
  },
];

@NgModule({
  declarations: [
    DeviceListComponent,
    CreateDeviceComponent,
    DeviceDetailsComponent,
    AddUserComponent,
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    ReactiveFormsModule,
    WrappersModule,
    UserModule,
  ],
})
export class DevicesModule {}
