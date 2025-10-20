import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPayments } from './view-payments';

describe('ViewPayments', () => {
  let component: ViewPayments;
  let fixture: ComponentFixture<ViewPayments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewPayments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewPayments);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
