import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemoryGraphComponent } from './memory-graph.component';

describe('MemoryGraphComponent', () => {
  let component: MemoryGraphComponent;
  let fixture: ComponentFixture<MemoryGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemoryGraphComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemoryGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
