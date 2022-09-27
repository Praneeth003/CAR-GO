import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingPreviewComponent } from './booking-preview.component';

describe('BookingPreviewComponent', () => {
  let component: BookingPreviewComponent;
  let fixture: ComponentFixture<BookingPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookingPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookingPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
