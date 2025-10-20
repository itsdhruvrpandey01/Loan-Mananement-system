import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Listofloans } from './listofloans';

describe('Listofloans', () => {
  let component: Listofloans;
  let fixture: ComponentFixture<Listofloans>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Listofloans]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Listofloans);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
