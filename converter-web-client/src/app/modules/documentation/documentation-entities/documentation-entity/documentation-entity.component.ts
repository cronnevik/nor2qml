import { Component, OnInit, Input } from '@angular/core';
import { MappingAttributes } from 'src/app/shared/models/mappingattributes.model';

@Component({
  selector: 'app-documentation-entity',
  templateUrl: './documentation-entity.component.html',
  styleUrls: ['./documentation-entity.component.scss']
})
export class DocumentationEntityComponent implements OnInit {

  @Input() entity: MappingAttributes[];

  constructor() { }

  ngOnInit() {
  }

}
