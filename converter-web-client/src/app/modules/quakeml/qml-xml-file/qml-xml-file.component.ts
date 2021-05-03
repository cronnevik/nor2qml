import { Component, OnInit } from "@angular/core";
import { environment } from "src/environments/environment";
import { QmlToSfileService } from "src/app/core/http/qml-to-sfile.service";
import { Router, ActivatedRoute } from "@angular/router";
import { FormOption } from "../../../shared/models/formoption.model";

@Component({
  selector: "app-qml-xml-file",
  templateUrl: "./qml-xml-file.component.html",
  styleUrls: ["./qml-xml-file.component.scss"],
})
export class QmlXmlFileComponent implements OnInit {
  options = {
    data: {
      url: "",
      text: "",
    },
    version: "nordic1",
    errorHandling: "report",
  };

  baseUrl = environment.baseUrl;

  uploadConfig = {
    multiple: false,
    formatsAllowed: ".xml",
    maxSize: "30",
    uploadAPI: {
      url: this.baseUrl + "/read-xml-file",
      method: "POST",
      headers: {
        "nordic-version": this.options.version,
      },
      responseType: "text",
    },
    fileNameIndex: false,
    replaceTexts: {
      selectFileBtn: "Select QuakeML file",
      resetBtn: "Reset",
      uploadBtn: "Upload",
      dragNDropBox: "Drag N Drop",
      attachPinBtn: "Attach Files...",
      afterUploadMsg_success: "Successfully Uploaded !",
      afterUploadMsg_error: "Upload Failed !",
      sizeLimit: "Size Limit",
    },
  };

  qmlVersions: FormOption[] = [
    new FormOption("Nordic1", "nordic1"),
    new FormOption("Nordic2", "nordic2"),
  ];

  public loading = false;

  constructor(
    private qmlToSfileService: QmlToSfileService,
    private router: Router,
    private currentActivatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {}

  async fileReadResponse(data) {
    this.loading = true;
    await this.qmlToSfileService.setSfileString(data.body);
    this.loading = false;
    this.router.navigate(["../view-sfiles"], {
      relativeTo: this.currentActivatedRoute,
    });
  }

  onVerOptionChange(opt: FormOption) {
    this.options.version = opt.value;
    this.uploadConfig.uploadAPI.headers["nordic-version"] = opt.value;
  }
}
