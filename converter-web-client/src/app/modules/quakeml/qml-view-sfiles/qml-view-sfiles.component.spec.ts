import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QmlViewSfilesComponent } from './qml-view-sfiles.component';

describe('QmlViewSfilesComponent', () => {
  let component: QmlViewSfilesComponent;
  let fixture: ComponentFixture<QmlViewSfilesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QmlViewSfilesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QmlViewSfilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
