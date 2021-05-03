import { PropertyObject } from './propertyobject.model';

export class LineE extends Object {

    public covarianceXY: PropertyObject;
    public covarianceXZ: PropertyObject;
    public covarianceYZ: PropertyObject;
    public depthError: PropertyObject;
    public gap: PropertyObject;
    public latitudeError: PropertyObject;

    public lineNumber: string;
    public lineText: string;

    public longitudeError: PropertyObject;
    public originTimeError: PropertyObject;

    public rowNumber: string;

    public textGap: PropertyObject;

    constructor() { super(); }
}
