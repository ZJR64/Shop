import { TestBed } from '@angular/core/testing';

import { SafeGuardService } from './safe-guard.service';

describe('SafeGaurdService', () => {
  let service: SafeGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SafeGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
