import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Applyforloan } from './applyforloan';

describe('Applyforloan', () => {
  let component: Applyforloan;
  let fixture: ComponentFixture<Applyforloan>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Applyforloan]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Applyforloan);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
