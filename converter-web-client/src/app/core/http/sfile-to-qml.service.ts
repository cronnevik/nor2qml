import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { environment } from '../../../environments/environment';
import { Sfile } from 'src/app/shared/models/sfile.model';
import { ErrorSfile } from '../../shared/models/errorSfile.model';
import {IgnoredError} from "../../shared/models/ignoredError.model";
declare var require: any;

@Injectable()
export class SfileToQmlService {

    baseUrl = environment.baseUrl;
    sfileData: Sfile[];
    testData: string;
    version: string;

    error: IgnoredError[];

    file: any;

    private fileReturned = new BehaviorSubject<boolean>(false);
    isFileReturned = this.fileReturned.asObservable();

    constructor(private httpClient: HttpClient) {}

    public resetLineData() {
        this.sfileData = null;
    }

    public setLinesData(lines: Sfile[]) {
        if (this.sfileData == null || this.sfileData.length < 0) {
            this.sfileData = lines;
        } else {
            lines.forEach(line => {
                this.sfileData.push(line);
            });
        }
    }

    public getLinesData(): Sfile[] {
        return this.sfileData;
    }

    public setQmlVersion(val: string) {
        this.version = val;
    }

    public getQmlVersion(): string {
        return this.version;
    }

    public generateXml(data, options) {
        const self = this;

        const payload = {
            'sfiles' : data,
            'options': options
        };

        this.version = options.version;

        const promise = new Promise((resolve, reject) => {
            this.httpClient.post(this.baseUrl + '/generate-xml', payload, {responseType: 'text'})
                .toPromise()
                .then(
                    (res: any) => {
                        const data = JSON.parse(res);
                        this.file = new Blob([data.qml], { type: 'text/plain;charset=utf-8' });
                        this.error = data.errors;
                        this.fileReturned.next(true);
                        resolve();
                    },

                    (rej) => {
                        this.error = JSON.parse(rej.error);
                        reject();
                    }
                );
        });
        return promise;
    }

    public getTestData(): string {
        return this.testData;
    }

    public getXmlFile() {
        return this.file;
    }

    public isFileReceived() {
        return this.fileReturned;
    }

    public getError() {
        return this.error;
    }

}
