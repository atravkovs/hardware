import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../shared/user/models/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserRepositoryService {
  constructor(private http: HttpClient) {}

  getCurrentUser(): Observable<User> {
    return this.http.get<User>('/api/users/user');
  }
}
