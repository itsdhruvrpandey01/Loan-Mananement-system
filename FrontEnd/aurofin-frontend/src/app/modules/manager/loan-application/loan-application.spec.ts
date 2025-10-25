import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanApplication } from './loan-application';

describe('LoanApplication', () => {
  let component: LoanApplication;
  let fixture: ComponentFixture<LoanApplication>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoanApplication]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanApplication);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
