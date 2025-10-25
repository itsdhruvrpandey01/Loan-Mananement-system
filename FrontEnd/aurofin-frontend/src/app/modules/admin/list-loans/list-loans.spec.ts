import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListLoans } from './list-loans';

describe('ListLoans', () => {
  let component: ListLoans;
  let fixture: ComponentFixture<ListLoans>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListLoans]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListLoans);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
