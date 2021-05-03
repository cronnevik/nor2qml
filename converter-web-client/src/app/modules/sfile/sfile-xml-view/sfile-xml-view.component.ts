import { Component, OnInit } from '@angular/core';
import { SfileToQmlService } from 'src/app/core/http/sfile-to-qml.service';
import { saveAs } from 'file-saver';
import { environment } from 'src/environments/environment';
declare var require: any;


@Component({
  selector: 'app-sfile-xml-view',
  templateUrl: './sfile-xml-view.component.html',
  styleUrls: ['./sfile-xml-view.component.scss']
})
export class SfileXmlViewComponent implements OnInit {

  baseUrl = environment.baseUrl;

  filename = '';
  fileReceived = false;
  eventErrors = [];

  constructor(
    private sfileToQmlService: SfileToQmlService
  ) { }

  ngOnInit() {
    this.sfileToQmlService.isFileReturned.subscribe(
      data => {
        this.fileReceived = data;
        this.eventErrors = this.sfileToQmlService.getError();
      }
    );
  }

  saveXmlFile() {
    let newFilename;
    const file = new Blob([this.sfileToQmlService.getXmlFile()], { type: 'text/xml;charset=utf-8' });
    if (this.filename === '') {
      newFilename = 'quakeml';
    } else {
      newFilename = this.filename;
    }
    saveAs(file, newFilename + '.xml');

  }

}
