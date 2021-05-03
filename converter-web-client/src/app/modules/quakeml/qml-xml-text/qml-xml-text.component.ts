import { Component, OnInit } from "@angular/core";
import { QmlToSfileService } from "src/app/core/http/qml-to-sfile.service";
import { Router, ActivatedRoute } from "@angular/router";
import { FormOption } from "../../../shared/models/formoption.model";

@Component({
  selector: "app-qml-xml-text",
  templateUrl: "./qml-xml-text.component.html",
  styleUrls: ["./qml-xml-text.component.scss"],
})
export class QmlXmlTextComponent implements OnInit {
  xmlText: string;

  public loading = false;

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

  async readXmlText() {
    this.loading = true;
    await this.qmlToSfileService.getEventDataFromRawText(this.options);
    this.loading = false;
    this.router.navigate(["../view-sfiles"], {
      relativeTo: this.currentActivatedRoute,
    });
  }

  onVerOptionChange(opt: FormOption) {
    this.options.version = opt.value;
  }
}
