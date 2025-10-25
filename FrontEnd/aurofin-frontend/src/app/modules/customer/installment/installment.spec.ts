import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Installment } from './installment';

describe('Installment', () => {
  let component: Installment;
  let fixture: ComponentFixture<Installment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Installment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Installment);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
