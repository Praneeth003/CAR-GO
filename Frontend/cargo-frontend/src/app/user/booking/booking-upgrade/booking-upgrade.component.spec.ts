import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingUpgradeComponent } from './booking-upgrade.component';

describe('BookingUpgradeComponent', () => {
  let component: BookingUpgradeComponent;
  let fixture: ComponentFixture<BookingUpgradeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookingUpgradeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookingUpgradeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
