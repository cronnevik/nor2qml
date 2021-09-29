import { Component, OnInit } from '@angular/core';
import { SfileToQmlService } from 'src/app/core/http/sfile-to-qml.service';
import { Router, ActivatedRoute } from '@angular/router';
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
import {IgnoredError} from "../../../shared/models/ignoredError.model";
import {SfileObj} from "../../../shared/models/sfileObj.model";
import {SfileData} from "../../../shared/models/sfileData";
import {Line4Dto} from "../../../shared/models/line4Dto.model";
import {LineM1} from "../../../shared/models/lineM1.model";

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
  events: SfileObj[];

  language = 'xml';
  interpolate = {
    language: 'language interpolated'
  };

  selectedRaw: SfileObj;
  selectedEvent: SfileObj;

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
      this.events.forEach(ev => {
        if (ev.version ==='VERSION1') {
          ev.type = 'sfilenordic';
        } else if (ev.version ==='VERSION2') {
          ev.type = 'sfilenordic2';
        }
      })
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

  userSelectRaw(ev: SfileObj) {
    this.selectedRaw = ev;
    this.textRawOutput = '12345678901234567890123456789012345678901234567890123456789012345678901234567890' + '\n';

    const data: SfileData = this.events[this.events.indexOf(this.selectedRaw)].data;

    if (data.line1s) {
      data.line1s.forEach(l1 => {
        this.textRawOutput += (l1.lineText + '\n');
      });
    }
    if (data.lineEs) {
      data.lineEs.forEach(le => {
        this.textRawOutput += (le.lineText + '\n');
      });
    }
    if (data.lineIs) {
      data.lineIs.forEach(lI => {
        this.textRawOutput += (lI.lineText + '\n');
      });
    }
    if (data.lineFs) {
      data.lineFs.forEach(lf => {
        this.textRawOutput += (lf.lineText + '\n');
      });
    }
    if (data.lineM1s) {
      data.lineM1s.forEach(lM1 => {
        this.textRawOutput += (lM1.lineText + '\n');
      });
    }
    if (data.lineM2s) {
      data.lineM2s.forEach(lM2 => {
        this.textRawOutput += (lM2.lineText + '\n');
      });
    }
    if (data.line2s) {
      data.line2s.forEach(l2 => {
        this.textRawOutput += (l2.lineText + '\n');
      });
    }
    if (data.line3s) {
      data.line3s.forEach(l3 => {
        this.textRawOutput += (l3.lineText + '\n');
      });
    }
    if (data.line5s) {
      data.line5s.forEach(l5 => {
        this.textRawOutput += (l5.lineText + '\n');
      });
    }
    if (data.line6s) {
      data.line6s.forEach(l6 => {
        this.textRawOutput += (l6.lineText + '\n');
      });
    }
    if (data.line4s) {
      if (ev.version === 'VERSION1') {
        this.textRawOutput += ' STAT SP IPHASW D HRMM SECON CODA AMPLIT PERI AZIMU VELO SNR AR TRES W  DIS CAZ7' + '\n';
      } else if (ev.version === 'VERSION2') {
        this.textRawOutput += ' STAT COM NTLO IPHASE   W HHMM SS.SSS   PAR1  PAR2 AGA OPE  AIN  RES W  DIS CAZ7' + '\n';
      }
      data.line4s.forEach(l4 => {
        this.textRawOutput += (l4.lineText + '\n');
      });
    }
  }

  userSelectLine(event: SfileObj, type: string) {
    this.selectedEvent = event;

    if (this.events.includes(event)) {
      this.changeType = type;
      if (type === 'line1') {
        const line1s: Line1[] = this.events[this.events.indexOf(event)].data.line1s;
        this.selectedLine = line1s;
      } else if (type === 'line2') {
        const line2s: Line2[] = this.events[this.events.indexOf(event)].data.line2s;
        this.selectedLine = line2s;
      } else if (type === 'line3') {
        const line3s: Line3[] = this.events[this.events.indexOf(event)].data.line3s;
        this.selectedLine = line3s;
      } else if (type === 'line4') {
        if (event.version === 'VERSION1') {
          const line4s: Line4[] = this.events[this.events.indexOf(event)].data.line4s;
          this.selectedLine = line4s;
        } else if(event.version === 'VERSION2') {
          const line4s: Line4Dto[] = this.events[this.events.indexOf(event)].data.line4s;
          this.selectedLine = line4s;
        }
      } else if (type === 'line5') {
        const line5s: Line5[] = this.events[this.events.indexOf(event)].data.line5s;
        this.selectedLine = line5s;
      } else if (type === 'line6') {
        const line6s: Line6[] = this.events[this.events.indexOf(event)].data.line6s;
        this.selectedLine = line6s;
      } else if (type === 'lineE') {
        const lineEs: LineE[] = this.events[this.events.indexOf(event)].data.lineEs;
        this.selectedLine = lineEs;
      } else if (type === 'lineF') {
        const lineFs: LineF[] = this.events[this.events.indexOf(event)].data.lineFs;
        this.selectedLine = lineFs;
      } else if (type === 'lineI') {
        const lineIs: LineI[] = this.events[this.events.indexOf(event)].data.lineIs;
        this.selectedLine = lineIs;
      } else if (type === 'lineM1s') {
        const lineM1s: LineM1[] = this.events[this.events.indexOf(event)].data.lineM1s;
        this.selectedLine = lineM1s;
      }else if (type === 'lineM2s') {
        const lineM2s: LineM2[] = this.events[this.events.indexOf(event)].data.lineM2s;
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
        this.events[eventIndex].data.line1s = <[Line1]> this.selectedLine;
        break;
      }
      case 'line2': {
        this.events[eventIndex].data.line2s = <[Line2]> this.selectedLine;
        break;
      }
      case 'line3': {
        this.events[eventIndex].data.line3s = <[Line3]> this.selectedLine;
        break;
      }
      case 'line4': {
        this.events[eventIndex].data.line4s = this.selectedLine;
        break;
      }
      case 'line5': {
        this.events[eventIndex].data.line5s = <[Line5]> this.selectedLine;
        break;
      }
      case 'line6': {
        this.events[eventIndex].data.line6s = <[Line6]> this.selectedLine;
        break;
      }
      case 'lineE': {
        this.events[eventIndex].data.lineEs = <[LineE]> this.selectedLine;
        break;
      }
      case 'lineF': {
        this.events[eventIndex].data.lineFs = <[LineF]> this.selectedLine;
        break;
      }
      case 'lineI': {
        this.events[eventIndex].data.lineIs = <[LineI]> this.selectedLine;
        break;
      }
      case 'lineM1s': {
        this.events[eventIndex].data.lineM1s = <[LineM1]> this.selectedLine;
        break;
      }
      case 'lineM2s': {
        this.events[eventIndex].data.lineM2s = <[LineM2]> this.selectedLine;
        break;
      }
    }
  }

  deleteEventFromList(event: SfileObj) {
    const eventIndex = this.events.indexOf(event);
    this.events.splice(eventIndex, 1);
  }

  clearSelected() {
    this.selectedLine = null;
    this.selectedEvent = null;
  }

}
