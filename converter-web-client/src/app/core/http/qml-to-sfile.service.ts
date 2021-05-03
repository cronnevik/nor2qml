import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class QmlToSfileService {
  baseUrl = environment.baseUrl;
  sfileString: string;

  error = {
    message: "",
  };

  constructor(private httpClient: HttpClient) {}

  public getEventDataFromRawText(options: object) {
    const self = this;
    const getUrl = this.baseUrl + "/read-qml-rawtext";

    const promise = new Promise((resolve, reject) => {
      this.httpClient
        .post(getUrl, options, { observe: "body", responseType: "text" })
        .toPromise()
        .then(
          (res) => {
            self.sfileString = res.toString();
            resolve();
          },
          (rej) => {
            this.error.message = rej.message;
            reject();
          }
        );
    });

    return promise;
  }

  public getEventData(options: object) {
    const self = this;
    const getUrl = this.baseUrl + "/read-qml-service";

    const promise = new Promise((resolve, reject) => {
      this.httpClient
        .post(getUrl, options, { observe: "body", responseType: "text" })
        .toPromise()
        .then(
          (res) => {
            self.sfileString = res.toString();
            resolve();
          },
          (rej) => {
            this.error.message = rej.message;
            console.log(this.error.message);
            reject();
          }
        );
    });

    return promise;
  }

  public setSfileString(value: string) {
    this.sfileString = value;
  }

  public getSfileString(): string {
    return this.sfileString;
  }

  public getError() {
    return this.error;
  }
}
