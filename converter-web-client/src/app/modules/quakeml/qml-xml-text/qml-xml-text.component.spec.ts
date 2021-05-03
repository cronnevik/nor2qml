import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QmlXmlTextComponent } from './qml-xml-text.component';

describe('QmlXmlTextComponent', () => {
  let component: QmlXmlTextComponent;
  let fixture: ComponentFixture<QmlXmlTextComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QmlXmlTextComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QmlXmlTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
