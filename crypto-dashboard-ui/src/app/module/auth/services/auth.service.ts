import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { concatMap, tap } from 'rxjs';
import { LoginDTO, RegistrationDTO, registrationDtoToLoginDto } from '../models/login.model';
import { TokenService } from './token.service';
import { AuthenticationRepository } from './authentication.repository';

@Injectable()
export class AuthService {
  constructor(
    public userRepository: AuthenticationRepository,
    public tokenService: TokenService,
    public router: Router
  ) {}

  isLoggedIn(): boolean {
    return this.tokenService.isLoggedIn();
  }

  login(loginDto: LoginDTO) {
    return this.userRepository.login(loginDto).pipe(
      tap((jwtResponse) => this.tokenService.setToken(jwtResponse.jwtToken)),
      tap(() => this.router.navigate(['/']))
    );
  }

  register(registrationDto: RegistrationDTO) {
    return this.userRepository
      .register(registrationDto)
      .pipe(
        concatMap(() => this.login(registrationDtoToLoginDto(registrationDto)))
      );
  }

  logout() {
    this.tokenService.logout();
    this.router.navigate(['/login']);
  }
}
