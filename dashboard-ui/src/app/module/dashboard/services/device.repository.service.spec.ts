import { TestBed } from '@angular/core/testing';

import { DeviceRepositoryService } from './device.repository.service';

describe('DeviceRepositoryService', () => {
  let service: DeviceRepositoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceRepositoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
