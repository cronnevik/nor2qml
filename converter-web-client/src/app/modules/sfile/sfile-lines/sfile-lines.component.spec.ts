import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SfileLinesComponent } from './sfile-lines.component';

describe('SfileLinesComponent', () => {
  let component: SfileLinesComponent;
  let fixture: ComponentFixture<SfileLinesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SfileLinesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SfileLinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
