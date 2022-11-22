import { Injectable } from '@angular/core';

@Injectable()
export class TokenService {
  public setToken(token: string) {
    localStorage.setItem('token', token);
  }

  public getToken(): string | null {
    return localStorage.getItem('token');
  }

  public isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  public logout() {
    return localStorage.removeItem('token');
  }
}
