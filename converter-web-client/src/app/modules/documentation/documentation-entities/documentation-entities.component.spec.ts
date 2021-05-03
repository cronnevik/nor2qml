import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentationEntitiesComponent } from './documentation-entities.component';

describe('DocumentationEntitiesComponent', () => {
  let component: DocumentationEntitiesComponent;
  let fixture: ComponentFixture<DocumentationEntitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentationEntitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentationEntitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
