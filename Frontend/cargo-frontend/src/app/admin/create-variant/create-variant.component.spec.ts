import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateVariantComponent } from './create-variant.component';

describe('CreateVariantComponent', () => {
  let component: CreateVariantComponent;
  let fixture: ComponentFixture<CreateVariantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateVariantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateVariantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
