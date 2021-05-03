import { PropertyObject } from './propertyobject.model';

export class Line1 extends Object {
    public day: PropertyObject;
    public depth: PropertyObject;
    public depthIndicator: PropertyObject;
    public distanceIndicator: PropertyObject;
    public eventID: PropertyObject;
    public fixOfTime: PropertyObject;
    public hour: PropertyObject;
    public hypoCenterRepAgency: PropertyObject;
    public latitude: PropertyObject;

    public lineNumber: string;
    public lineText: string;

    public locIndicator: PropertyObject;
    public locModelIndicator: PropertyObject;
    public longitude: PropertyObject;
    public magOneNo: PropertyObject;
    public magOneRepAgency: PropertyObject;
    public magOneType: PropertyObject;
    public magThreeNo: PropertyObject;
    public magThreeRepAgency: PropertyObject;
    public magThreeType: PropertyObject;
    public magTwoNo: PropertyObject;
    public magTwoRepAgency: PropertyObject;
    public magTwoType: PropertyObject;
    public minutes: PropertyObject;
    public month: PropertyObject;
    public numStationsUsed: PropertyObject;

    public orgID: string;

    public rmsTimeResiduals: PropertyObject;

    public rowNumber: string;

    public seconds: PropertyObject;
    public year: PropertyObject;

    constructor() {
        super();
    }
}
