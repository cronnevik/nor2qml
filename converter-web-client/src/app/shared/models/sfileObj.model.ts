import {SfileData} from "./sfileData";

export class SfileObj extends Object {
  public filename: string;
  public data: SfileData;
  public eventParamsId: string;
  public version: string;
  public type: string;

  constructor() {
    super();
  }
}
