import { Component, OnInit } from '@angular/core';
import { SfileToQmlService } from 'src/app/core/http/sfile-to-qml.service';
import { Sfile } from 'src/app/shared/models/sfile.model';
import { Router, ActivatedRoute } from '@angular/router';
import { ErrorMap } from '../../../shared/models/errormap.model';
import { FormOption } from 'src/app/shared/models/formoption.model';
import * as _ from 'lodash';
import { Line1 } from 'src/app/shared/models/line1.model';
import { Line2 } from 'src/app/shared/models/line2.model';
import { Line3 } from 'src/app/shared/models/line3.model';
import { Line4 } from 'src/app/shared/models/line4.model';
import { Line5 } from 'src/app/shared/models/line5.mode';
import { Line6 } from 'src/app/shared/models/line6.model';
import { LineE } from 'src/app/shared/models/lineE.model';
import { LineF } from 'src/app/shared/models/lineF.mode';
import { LineI } from 'src/app/shared/models/lineI.model';
import { LineM2 } from 'src/app/shared/models/lineM2.model';
import { PropertyObject } from 'src/app/shared/models/propertyobject.model';
import { isObject } from 'util';
import { ErrorSfile } from '../../../shared/models/errorSfile.model';
import {IgnoredError} from "../../../shared/models/ignoredError.model";

@Component({
  selector: 'app-sfile-lines',
  templateUrl: './sfile-lines.component.html',
  styleUrls: ['./sfile-lines.component.scss']
})
export class SfileLinesComponent implements OnInit {

  qmlVersions: FormOption[] = [
    new FormOption('QuakeML v1.2', 'v12'),
    new FormOption('QuakeML v2.0', 'v20')
  ];

  qmlPrefix: FormOption[] = [
    new FormOption('quakeml', 'quakeml'),
    new FormOption('smi', 'smi')
  ];

  errorHandling: FormOption[] = [
    new FormOption('Remove event(s) automatically on error', 'ignore'),
    new FormOption('Report on error and edit in list', 'report')
  ];

  options = {
    id: {
      prefix: 'quakeml',
      agencyID: ''
    },
    version: 'v12',
    errorHandling: 'report'
  };
  error: IgnoredError[] = null;
  events: Sfile[];

  language = 'xml';
  interpolate = {
    language: 'language interpolated'
  };

  selectedRaw: Sfile;
  selectedEvent: Sfile;

  selectedLine: Object[];

  textRawOutput: string;
  lineKeys: string[] = [];
  lineValues: Object[] = [];

  changeType: string;
  objectKeys = Object.keys;
  objectValues = Object.values;

  constructor(
    private sfileToQmlService: SfileToQmlService,
    private router: Router,
    private currentActivatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    this.events = this.sfileToQmlService.getLinesData();
  }

  async configXml() {
    if (this.events == null) {
      // this.error = new ErrorMap('No file has been uploaded');
    } else if (this.events.length <= 0) {
      // this.error = new ErrorMap('Empty S-file list');
    } else {
      try {
        await this.sfileToQmlService.generateXml(this.events, this.options);
        this.router.navigate(['../xml-download'], {relativeTo: this.currentActivatedRoute});
      } catch (err) {
        this.error = this.sfileToQmlService.getError();
      }
    }
  }

  onVerOptionChange(opt: FormOption) {
    this.options.version = opt.value;
  }

  onErrOptionChange(opt: FormOption) {
    this.options.errorHandling = opt.value;
  }

  userSelectRaw(ev: Sfile) {
    this.selectedRaw = ev;
    this.textRawOutput = '12345678901234567890123456789012345678901234567890123456789012345678901234567890' + '\n';
    if (ev.line1) {
      ev.line1.forEach(l1 => {
        this.textRawOutput += (l1.lineText + '\n');
      });
    }
    if (ev.lineE) {
      ev.lineE.forEach(le => {
        this.textRawOutput += (le.lineText + '\n');
      });
    }
    if (ev.lineI) {
      ev.lineI.forEach(lI => {
        this.textRawOutput += (lI.lineText + '\n');
      });
    }
    if (ev.lineF) {
      ev.lineF.forEach(lf => {
        this.textRawOutput += (lf.lineText + '\n');
      });
    }
    if (ev.lineM1s) {
      ev.lineM1s.forEach(lM1 => {
        this.textRawOutput += (lM1.lineText + '\n');
      });
    }
    if (ev.lineM2s) {
      ev.lineM2s.forEach(lM2 => {
        this.textRawOutput += (lM2.lineText + '\n');
      });
    }
    if (ev.line2) {
      ev.line2.forEach(l2 => {
        this.textRawOutput += (l2.lineText + '\n');
      });
    }
    if (ev.line3) {
      ev.line3.forEach(l3 => {
        this.textRawOutput += (l3.lineText + '\n');
      });
    }
    if (ev.line5) {
      ev.line5.forEach(l5 => {
        this.textRawOutput += (l5.lineText + '\n');
      });
    }
    if (ev.line6) {
      ev.line6.forEach(l6 => {
        this.textRawOutput += (l6.lineText + '\n');
      });
    }
    if (ev.line4) {
      this.textRawOutput += ' STAT SP IPHASW D HRMM SECON CODA AMPLIT PERI AZIMU VELO SNR AR TRES W  DIS CAZ7' + '\n';
      ev.line4.forEach(l4 => {
        this.textRawOutput += (l4.lineText + '\n');
      });
    }
  }

  userSelectLine(event: Sfile, type: string) {
    this.selectedEvent = event;

    if (this.events.includes(event)) {
      this.changeType = type;
      if (type === 'line1') {
        const line1s: Line1[] = this.events[this.events.indexOf(event)].line1;
        this.selectedLine = line1s;
      } else if (type === 'line2') {
        const line2s: Line2[] = this.events[this.events.indexOf(event)].line2;
        this.selectedLine = line2s;
      } else if (type === 'line3') {
        const line3s: Line3[] = this.events[this.events.indexOf(event)].line3;
        this.selectedLine = line3s;
      } else if (type === 'line4') {
        const line4s: Line4[] = this.events[this.events.indexOf(event)].line4;
        this.selectedLine = line4s;
      } else if (type === 'line5') {
        const line5s: Line5[] = this.events[this.events.indexOf(event)].line5;
        this.selectedLine = line5s;
      } else if (type === 'line6') {
        const line6s: Line6[] = this.events[this.events.indexOf(event)].line6;
        this.selectedLine = line6s;
      } else if (type === 'lineE') {
        const lineEs: LineE[] = this.events[this.events.indexOf(event)].lineE;
        this.selectedLine = lineEs;
      } else if (type === 'lineF') {
        const lineFs: LineF[] = this.events[this.events.indexOf(event)].lineF;
        this.selectedLine = lineFs;
      } else if (type === 'lineI') {
        const lineIs: LineI[] = this.events[this.events.indexOf(event)].lineI;
        this.selectedLine = lineIs;
      } else if (type === 'lineM1s') {
        const lineM1s: LineM2[] = this.events[this.events.indexOf(event)].lineM1s;
        this.selectedLine = lineM1s;
      }else if (type === 'lineM2s') {
        const lineM2s: LineM2[] = this.events[this.events.indexOf(event)].lineM2s;
        this.selectedLine = lineM2s;
      }
    }
  }

  transformObjectKey(val: string) {
    // Use Lodash lib to transform camelCase text to Sentence Case Text
    return _.startCase(val);
  }

  isObjectNotRelation(val: any) {
    if (typeof val === 'object') {
      const obj: Object = <Object> val;
      // Check for relation (common property in a line type)
      if (obj.hasOwnProperty('lineText')) {
        return false;
      }
      return true;
    }
    return false;
  }

  saveEditedLines() {
    const eventIndex = this.events.indexOf(this.selectedEvent);
    switch (this.changeType) {
      case 'line1': {
        this.events[eventIndex].line1 = <[Line1]> this.selectedLine;
        break;
      }
      case 'line2': {
        this.events[eventIndex].line2 = <[Line2]> this.selectedLine;
        break;
      }
      case 'line3': {
        this.events[eventIndex].line3 = <[Line3]> this.selectedLine;
        break;
      }
      case 'line4': {
        this.events[eventIndex].line4 = <[Line4]> this.selectedLine;
        break;
      }
      case 'line5': {
        this.events[eventIndex].line5 = <[Line5]> this.selectedLine;
        break;
      }
      case 'line6': {
        this.events[eventIndex].line6 = <[Line6]> this.selectedLine;
        break;
      }
      case 'lineE': {
        this.events[eventIndex].lineE = <[LineE]> this.selectedLine;
        break;
      }
      case 'lineF': {
        this.events[eventIndex].lineF = <[LineF]> this.selectedLine;
        break;
      }
      case 'lineI': {
        this.events[eventIndex].lineI = <[LineI]> this.selectedLine;
        break;
      }
      case 'lineM2s': {
        this.events[eventIndex].lineM2s = <[LineM2]> this.selectedLine;
        break;
      }
    }
  }

  deleteEventFromList(event: Sfile) {
    const eventIndex = this.events.indexOf(event);
    this.events.splice(eventIndex, 1);
  }

  clearSelected() {
    this.selectedLine = null;
    this.selectedEvent = null;
  }

}
