import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Addmanager } from './addmanager';

describe('Addmanager', () => {
  let component: Addmanager;
  let fixture: ComponentFixture<Addmanager>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Addmanager]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Addmanager);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
