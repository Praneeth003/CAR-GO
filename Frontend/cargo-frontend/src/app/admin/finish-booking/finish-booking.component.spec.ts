import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinishBookingComponent } from './finish-booking.component';

describe('FinishBookingComponent', () => {
  let component: FinishBookingComponent;
  let fixture: ComponentFixture<FinishBookingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinishBookingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinishBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
