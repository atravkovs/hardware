import { Component } from '@angular/core';
import { AuthService } from 'src/app/module/auth/services/auth.service';
import { TokenService } from 'src/app/module/auth/services/token.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  constructor(public authService: AuthService) {}

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  logout(event: Event) {
    event.preventDefault();
    this.authService.logout();
  }
}
