import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-quakeml',
  templateUrl: './quakeml.component.html',
  styleUrls: ['./quakeml.component.scss']
})
export class QuakemlComponent implements OnInit {

  link = '';
  xmlText = '';
  private file: any;

  sfileText = '';
  isCopied = false;

  interpolate = {
    language: 'language interpolated'
  };

  language = 'xml';

  constructor() { }

  ngOnInit() {
  }

  saveFile() {}

  readWebService() {}

  readXmlText() {}

  readXmlFile() {}

}
