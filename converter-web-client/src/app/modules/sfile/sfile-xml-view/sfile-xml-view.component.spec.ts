import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SfileXmlViewComponent } from './sfile-xml-view.component';

describe('SfileXmlViewComponent', () => {
  let component: SfileXmlViewComponent;
  let fixture: ComponentFixture<SfileXmlViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SfileXmlViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SfileXmlViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
