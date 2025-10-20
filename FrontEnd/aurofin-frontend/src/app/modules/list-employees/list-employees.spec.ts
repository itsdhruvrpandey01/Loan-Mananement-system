import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListEmployees } from './list-employees';

describe('ListEmployees', () => {
  let component: ListEmployees;
  let fixture: ComponentFixture<ListEmployees>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListEmployees]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListEmployees);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
