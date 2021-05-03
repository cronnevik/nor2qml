import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Router, ActivatedRoute } from '@angular/router';
import { SfileToQmlService } from 'src/app/core/http/sfile-to-qml.service';
import { FileUploader, FileItem, ParsedResponseHeaders } from 'ng2-file-upload';

const URL = environment.baseUrl + '/read-sfile';

@Component({
  selector: 'app-sfile',
  templateUrl: './sfile.component.html',
  styleUrls: ['./sfile.component.scss']
})
export class SfileComponent implements OnInit {

  public uploader: FileUploader = new FileUploader({url: URL});

  filChosen = true;
  public loading = false;
  error = null;

  constructor(
    private router: Router,
    private currentActivatedRoute: ActivatedRoute,
    private sfileToQmlService: SfileToQmlService
  ) { }

  ngOnInit() {
    this.error = null;
    this.sfileToQmlService.resetLineData();
    this.uploader.onErrorItem = (item: FileItem, response: string, status: number, headers: ParsedResponseHeaders): any => {
      this.error = {
        message: 'Cannot upload the file(s) due to internal server error'
      };
      this.loading = false;
    };

    this.uploader.onBeforeUploadItem = (fileItem: FileItem): any  => {
      this.loading = true;
    };

    this.uploader.onCompleteAll = () => {
      if (this.error == null) {
        this.router.navigate(['lines'], {relativeTo: this.currentActivatedRoute});
      } else {
        console.log('an error occured');
      }
     };
    this.uploader.onSuccessItem = (item: FileItem, response: string, status: number, headers: ParsedResponseHeaders) => {
      this.error = null;
      this.fileReadResponse(response);
      this.loading = false;
    };
  }

  async fileReadResponse(data) {
    const resp = JSON.parse(data);
    if (resp.exception == null) {
     await this.sfileToQmlService.setLinesData(resp);
    } else {
      this.error = {
        message: resp.message
      };
      this.loading = false;
    }
  }

}
