import { Component, OnInit } from '@angular/core';
import * as smoothscroll from 'smoothscroll-polyfill';
import * as event from './entities-data/event';
import * as origin from './entities-data/origin';
import * as originUncertainty from './entities-data/originuncertainty';
import * as magnitude from './entities-data/magnitude';
import * as focalMechanism from './entities-data/focalmechanism';
import * as momentTensor from './entities-data/momenttensor';
import * as stationmagnitude from './entities-data/stationmagnitude';
import * as stationmagnitudeContribution from './entities-data/stationmagnitudecont';
import * as arrival from './entities-data/arrival';
import * as pick from './entities-data/pick';
import * as amplitude from './entities-data/amplitude';

@Component({
  selector: 'app-documentation-entities',
  templateUrl: './documentation-entities.component.html',
  styleUrls: ['./documentation-entities.component.scss']
})
export class DocumentationEntitiesComponent implements OnInit {

  eventMapping = event.EventAttributes;
  originMapping = origin.OriginAttributes;
  originUncMapping = originUncertainty.OriginUncertaintyAttributes;
  magnitudeMapping = magnitude.MagnitudeAttributes;
  focalMechanismMapping = focalMechanism.FocalMechanismAttributes;
  momentTensorMapping = momentTensor.MomentTensorAttributes;
  stationMagnitudeMapping = stationmagnitude.StationMagnitudeAttributes;
  stationMagnitudeContributionMapping = stationmagnitudeContribution.StationMagnitudeContributionAttributes;
  arrivalMapping = arrival.ArrivalAttributes;
  pickMapping = pick.PickAttributes;
  amplitudeMapping = amplitude.AmplitudeAttributes;

  constructor() {smoothscroll.polyfill(); }

  ngOnInit() {
  }

  scrollToElement($element): void {
    $element.scrollIntoView(true);
    window.scrollBy(0, -105);
  }

}
