import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GamblePage } from './gamble-page';

describe('GamblePage', () => {
  let component: GamblePage;
  let fixture: ComponentFixture<GamblePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GamblePage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GamblePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
