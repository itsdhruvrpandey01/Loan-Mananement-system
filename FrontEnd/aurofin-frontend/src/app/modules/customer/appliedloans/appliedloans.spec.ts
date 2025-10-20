import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Appliedloans } from './appliedloans';

describe('Appliedloans', () => {
  let component: Appliedloans;
  let fixture: ComponentFixture<Appliedloans>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Appliedloans]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Appliedloans);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
