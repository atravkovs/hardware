import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Observable, Subject, switchMap } from 'rxjs';
import { User } from 'src/app/module/shared/user/models/user.model';
import { Page } from '../../../shared/models/page.model';
import { UserRepositoryService } from '../../../shared/user/services/user.repository.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
})
export class UserListComponent implements OnInit, AfterViewInit {
  refresh$: Subject<string> = new Subject();
  users$: Observable<Page<User>> | null = null;

  constructor(private userRepository: UserRepositoryService) {}

  ngOnInit(): void {
    this.users$ = this.refresh$.pipe(
      switchMap(() => {
        return this.userRepository.getUsers();
      })
    );
  }

  ngAfterViewInit(): void {
    this.refresh$.next('');
  }

  deleteUser(user: User) {
    this.userRepository.deleteUser(user.email).subscribe(() => {
      this.refresh$.next('');
    });
  }
}
