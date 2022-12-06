import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserListComponent } from './page/user-list/user-list.component';
import { RouterModule, Routes } from '@angular/router';
import { WrappersModule } from '../shared/wrappers/wrappers.module';
import { UserListByEmailsComponent } from './component/user-list-by-emails/user-list-by-emails.component';

const routes: Routes = [
  {
    path: '',
    component: UserListComponent,
  },
];

@NgModule({
  declarations: [UserListComponent, UserListByEmailsComponent],
  imports: [CommonModule, RouterModule.forChild(routes), WrappersModule],
  exports: [UserListByEmailsComponent],
})
export class UsersModule {}
