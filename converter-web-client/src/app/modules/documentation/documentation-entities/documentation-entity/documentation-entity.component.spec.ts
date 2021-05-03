import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentationEntityComponent } from './documentation-entity.component';

describe('DocumentationEntityComponent', () => {
  let component: DocumentationEntityComponent;
  let fixture: ComponentFixture<DocumentationEntityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentationEntityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentationEntityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
