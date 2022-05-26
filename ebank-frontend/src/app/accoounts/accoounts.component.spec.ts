import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccoountsComponent } from './accoounts.component';

describe('AccoountsComponent', () => {
  let component: AccoountsComponent;
  let fixture: ComponentFixture<AccoountsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccoountsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccoountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
