import { TestBed } from '@angular/core/testing';

import { HttperrorhandlerService } from './httperrorhandler.service';

describe('HttperrorhandlerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttperrorhandlerService = TestBed.get(HttperrorhandlerService);
    expect(service).toBeTruthy();
  });
});
