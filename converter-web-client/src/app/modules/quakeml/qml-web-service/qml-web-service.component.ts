import { Component, OnInit } from "@angular/core";
import { QmlToSfileService } from "src/app/core/http/qml-to-sfile.service";
import { Router, ActivatedRoute } from "@angular/router";
import { FormOption } from "../../../shared/models/formoption.model";

@Component({
  selector: "app-qml-web-service",
  templateUrl: "./qml-web-service.component.html",
  styleUrls: ["./qml-web-service.component.scss"],
})
export class QmlWebServiceComponent implements OnInit {
  public loading = false;
  error = null;

  qmlVersions: FormOption[] = [
    new FormOption("Nordic1", "nordic1"),
    new FormOption("Nordic2", "nordic2"),
  ];

  options = {
    data: {
      url: "",
      text: "",
    },
    version: "nordic1",
    errorHandling: "report",
  };

  constructor(
    private qmlToSfileService: QmlToSfileService,
    private router: Router,
    private currentActivatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {}

  async readWebService() {
    this.loading = true;
    if (this.options.data.url != null) {
      try {
        await this.qmlToSfileService.getEventData(this.options);
        this.loading = false;
        this.router.navigate(["../view-sfiles"], {
          relativeTo: this.currentActivatedRoute,
        });
      } catch (err) {
        this.error = this.qmlToSfileService.getError();
        this.loading = false;
      }
    } else {
    }
  }

  onVerOptionChange(opt: FormOption) {
    this.options.version = opt.value;
  }
}
