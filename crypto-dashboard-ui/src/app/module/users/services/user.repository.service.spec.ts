import { TestBed } from '@angular/core/testing';

import { UserRepositoryService } from './user.repository.service';

describe('UserServiceService', () => {
  let service: UserRepositoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserRepositoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
