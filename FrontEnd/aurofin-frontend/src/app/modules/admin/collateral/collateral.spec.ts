import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Collateral } from './collateral';

describe('Collateral', () => {
  let component: Collateral;
  let fixture: ComponentFixture<Collateral>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Collateral]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Collateral);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
