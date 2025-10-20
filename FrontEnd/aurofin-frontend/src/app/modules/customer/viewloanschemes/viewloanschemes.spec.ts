import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Viewloanschemes } from './viewloanschemes';

describe('Viewloanschemes', () => {
  let component: Viewloanschemes;
  let fixture: ComponentFixture<Viewloanschemes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Viewloanschemes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Viewloanschemes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
