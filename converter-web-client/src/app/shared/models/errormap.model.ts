export class ErrorMap {
    public timestamp: number;
    public status: number;
    public error: string;
    public exeption: string;
    public message: string;
    public path: string;

    constructor(message: string) {
        this.message = message;
    }
}
