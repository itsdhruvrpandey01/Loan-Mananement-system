import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Loanscheme } from './loanscheme';

describe('Loanscheme', () => {
  let component: Loanscheme;
  let fixture: ComponentFixture<Loanscheme>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Loanscheme]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Loanscheme);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
