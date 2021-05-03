import { PropertyObject } from './propertyobject.model';

export class Line4 extends Object {

    public amplitude: PropertyObject;
    public angleOfIncidence: PropertyObject;
    public azimuthAtSource: PropertyObject;
    public azimuthResidual: PropertyObject;
    public component: PropertyObject;
    public directionDegrees: PropertyObject;
    public duration: PropertyObject;
    public epicentralDistance: PropertyObject;
    public extLastPhaseIdChar: PropertyObject;
    public firstMotion: PropertyObject;
    public flagA: PropertyObject;
    public freeOrWeight: PropertyObject;
    public hour: PropertyObject;
    public instrumentType: PropertyObject;

    public lineNumber: string;
    public lineText: string;

    public minutes: PropertyObject;
    public periodSeconds: PropertyObject;
    public phaseID: PropertyObject;
    public phaseVelocity: PropertyObject;
    public qualityIndicator: PropertyObject;

    public rowNumber: string;

    public seconds: PropertyObject;
    public stationName: PropertyObject;
    public travelTimeResidual: PropertyObject;
    public weight: PropertyObject;
    public weightingIndicator: PropertyObject;

    constructor() { super(); }
}
