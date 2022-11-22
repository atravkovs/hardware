import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeviceListComponent } from './page/device-list/device-list.component';
import { WrappersModule } from '../shared/wrappers/wrappers.module';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: DeviceListComponent,
  },
];

@NgModule({
  declarations: [DeviceListComponent],
  imports: [CommonModule, RouterModule.forChild(routes), WrappersModule],
})
export class DevicesModule {}
