import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Vieweditloanscheme } from './vieweditloanscheme';

describe('Vieweditloanscheme', () => {
  let component: Vieweditloanscheme;
  let fixture: ComponentFixture<Vieweditloanscheme>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Vieweditloanscheme]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Vieweditloanscheme);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
