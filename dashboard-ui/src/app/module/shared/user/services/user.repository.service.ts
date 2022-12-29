import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../models/page.model';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserRepositoryService {
  constructor(private http: HttpClient) {}

  deleteUser(email: string): Observable<unknown> {
    return this.http.delete(`/api/users/user/${email}`);
  }

  getUsers(query: { search?: string }): Observable<Page<User>> {
    return this.http.get<Page<User>>('/api/users/users', {
      params: query,
    });
  }

  getUsersByEmails(emails: string[]): Observable<User[]> {
    return this.http.get<User[]>(`/api/users/users/emails`, {
      params: { emails },
    });
  }
}
