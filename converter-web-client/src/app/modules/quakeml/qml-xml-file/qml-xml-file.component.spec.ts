import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QmlXmlFileComponent } from './qml-xml-file.component';

describe('QmlXmlFileComponent', () => {
  let component: QmlXmlFileComponent;
  let fixture: ComponentFixture<QmlXmlFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QmlXmlFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QmlXmlFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
