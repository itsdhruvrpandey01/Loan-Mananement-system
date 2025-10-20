import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Loantype } from './loantype';

describe('Loantype', () => {
  let component: Loantype;
  let fixture: ComponentFixture<Loantype>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Loantype]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Loantype);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
