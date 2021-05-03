import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuakemlComponent } from './quakeml.component';

describe('QuakemlComponent', () => {
  let component: QuakemlComponent;
  let fixture: ComponentFixture<QuakemlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuakemlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuakemlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
