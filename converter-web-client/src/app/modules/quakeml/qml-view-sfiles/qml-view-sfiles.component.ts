import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { QmlToSfileService } from 'src/app/core/http/qml-to-sfile.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-qml-view-sfiles',
  templateUrl: './qml-view-sfiles.component.html',
  styleUrls: ['./qml-view-sfiles.component.scss']
})
export class QmlViewSfilesComponent implements OnInit {

  baseUrl = environment.baseUrl;

  sfileText: string;
  isCopied = false;

  interpolate = {
    language: 'language interpolated'
  };

  language = 'xml';
  filename = '';

  constructor(
    private qmlToSfileService: QmlToSfileService
  ) { }

  ngOnInit() {
    this.sfileText = this.qmlToSfileService.getSfileString();
  }

  saveFile() {
    let newFilename;
    const file = new Blob([this.sfileText], { type: 'text/plain;charset=utf-8' });
    if (this.filename === '') {
      newFilename = 'sfiles';
    } else {
      newFilename = this.filename;
    }
    saveAs(file, newFilename + '.out');
  }

}
