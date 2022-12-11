import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './page/main/main.component';
import { TranslateModule } from '@ngx-translate/core';
import { WrappersModule } from '../shared/wrappers/wrappers.module';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: MainComponent,
  },
];

@NgModule({
  declarations: [MainComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    TranslateModule,
    WrappersModule,
  ],
})
export class DashboardModule {}
