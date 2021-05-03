import { PropertyObject } from './propertyobject.model';

export class LineI extends Object {

    public dateTimeLastAction: PropertyObject;
    public helpTextAction: PropertyObject;
    public helpTextID: PropertyObject;
    public helpTextOperator: PropertyObject;
    public helpTextStatus: PropertyObject;
    public idLocked: PropertyObject;
    public idYearToSec: PropertyObject;
    public lastActionDone: PropertyObject;

    public lineContent: string;
    public lineNumber: string;
    public lineText: string;
    public lineType: string;

    public newFileIdCreated: PropertyObject;

    public rowNumber: string;

    public statusFlags: PropertyObject;

    constructor() { super(); }
}
