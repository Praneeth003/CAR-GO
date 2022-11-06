import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestUserCancelComponent } from './guest-user-cancel.component';

describe('GuestUserCancelComponent', () => {
  let component: GuestUserCancelComponent;
  let fixture: ComponentFixture<GuestUserCancelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GuestUserCancelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestUserCancelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
