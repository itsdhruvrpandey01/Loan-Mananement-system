import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Requestedloans } from './requestedloans';

describe('Requestedloans', () => {
  let component: Requestedloans;
  let fixture: ComponentFixture<Requestedloans>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Requestedloans]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Requestedloans);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
