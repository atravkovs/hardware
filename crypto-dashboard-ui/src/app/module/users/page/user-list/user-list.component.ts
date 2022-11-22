import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../../shared/models/page.model';
import { User } from '../../models/user.model';
import { UserRepositoryService } from '../../services/user.repository.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
})
export class UserListComponent implements OnInit {
  users$: Observable<Page<User>> | null = null;

  constructor(private userRepository: UserRepositoryService) {}

  ngOnInit(): void {
    this.users$ = this.userRepository.getUsers();
  }
}
