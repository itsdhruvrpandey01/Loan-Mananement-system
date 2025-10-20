import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Customerprofile } from './customerprofile';

describe('Customerprofile', () => {
  let component: Customerprofile;
  let fixture: ComponentFixture<Customerprofile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Customerprofile]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Customerprofile);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
