import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QmlWebServiceComponent } from './qml-web-service.component';

describe('QmlWebServiceComponent', () => {
  let component: QmlWebServiceComponent;
  let fixture: ComponentFixture<QmlWebServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QmlWebServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QmlWebServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
