import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeviceListComponent } from './page/device-list/device-list.component';
import { WrappersModule } from '../shared/wrappers/wrappers.module';
import { RouterModule, Routes } from '@angular/router';
import { CreateDeviceComponent } from './page/create-device/create-device.component';
import { ReactiveFormsModule } from '@angular/forms';

const routes: Routes = [
  {
    path: '',
    component: DeviceListComponent,
  },
  {
    path: 'create',
    component: CreateDeviceComponent,
  },
];

@NgModule({
  declarations: [DeviceListComponent, CreateDeviceComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    WrappersModule,
  ],
})
export class DevicesModule {}
