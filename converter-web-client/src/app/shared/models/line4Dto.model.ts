import { PropertyObject } from './propertyobject.model';

export class Line4Dto extends Object {
  public lineNumber: string;
  public lineText: string;
  public rowNumber: string;

  public stationName: PropertyObject;
  public component: PropertyObject;
  public networkCode: PropertyObject;
  public location: PropertyObject;
  public qualityIndicator: PropertyObject;
  public phaseID: PropertyObject;
  public weightingIndicator: PropertyObject;

  public freeOrFlag: PropertyObject;
  public hour: PropertyObject;
  public minutes: PropertyObject;
  public seconds: PropertyObject;
  public parameterOne: PropertyObject;
  public parameterTwo: PropertyObject;
  public agency: PropertyObject;
  public operator: PropertyObject;
  public angleOfIncidence: PropertyObject;
  public residual: PropertyObject;
  public weight: PropertyObject;
  public epicentralDistance: PropertyObject;
  public azimuthAtSource: PropertyObject;


  constructor() { super(); }
}
