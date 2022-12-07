import {
  AfterViewInit,
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { Observable, Subject, switchMap } from 'rxjs';
import { User } from '../../models/user.model';
import { UserRepositoryService } from '../../services/user.repository.service';

@Component({
  selector: 'app-user-list-by-emails',
  templateUrl: './user-list-by-emails.component.html',
  styleUrls: ['./user-list-by-emails.component.scss'],
})
export class UserListByEmailsComponent
  implements OnInit, AfterViewInit, OnChanges
{
  @Input()
  emails: string[] = [];

  email$: Subject<string[]>;
  users$: Observable<User[]> | null = null;

  constructor(private userRepository: UserRepositoryService) {
    this.email$ = new Subject();
  }

  ngAfterViewInit(): void {
    this.email$.next(this.emails);
  }

  ngOnInit(): void {
    this.users$ = this.email$.pipe(
      switchMap((emails) => {
        return this.userRepository.getUsersByEmails(emails);
      })
    );
  }

  ngOnChanges(_changes: SimpleChanges): void {
    this.email$.next(this.emails);
  }
}
