import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './page/login/login.component';
import { AuthCardComponent } from './component/auth-card/auth-card.component';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './page/register/register.component';
import { AuthenticationRepository } from './services/authentication.repository';
import { ReactiveFormsModule } from '@angular/forms';
import { TokenService } from './services/token.service';
import { AuthService } from './services/auth.service';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
];

@NgModule({
  declarations: [LoginComponent, AuthCardComponent, RegisterComponent],
  imports: [CommonModule, ReactiveFormsModule, RouterModule.forChild(routes)],
  providers: [AuthenticationRepository, TokenService, AuthService],
})
export class AuthModule {}
