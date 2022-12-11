import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './module/auth/guard/auth.guard';
import { UserRole } from './module/auth/models/user-role.enum';

const routes: Routes = [
  {
    path: 'devices',
    loadChildren: () =>
      import('./module/devices/devices.module').then((m) => m.DevicesModule),
  },
  {
    path: 'users',
    loadChildren: () =>
      import('./module/users/users.module').then((m) => m.UsersModule),
    canActivate: [AuthGuard],
    data: {
      roles: [UserRole.Admin],
    },
  },
  {
    path: '',
    loadChildren: () =>
      import('./module/dashboard/dashboard.module').then(
        (m) => m.DashboardModule
      ),
    canActivate: [AuthGuard],
    data: {
      roles: [UserRole.Admin, UserRole.User],
    },
  },
  {
    path: '',
    loadChildren: () =>
      import('./module/auth/auth.module').then((m) => m.AuthModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
