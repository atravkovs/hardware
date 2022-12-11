import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceUsersComponent } from './device-users.component';

describe('DeviceUsersComponent', () => {
  let component: DeviceUsersComponent;
  let fixture: ComponentFixture<DeviceUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeviceUsersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeviceUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
