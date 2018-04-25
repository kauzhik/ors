export class User {

  constructor(
    public firstName: string,
    public lastName: string,
    public email: string,
    public username: string,
    public password: string,
    public role: string,
    public deleteFlag: boolean,
    public mailingAddress: string,
    public city: string,
    public state: string,
    public zipCode: string,
    public deleteFlagAddress: boolean
  ) {}
}
