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
    public addressDeleteFlag: boolean,
    public userdeleteFlag: boolean,
    public userContact: string,
    public userContactNextSpring: boolean,
    public userContactNextSummer: boolean,
    public userEmergencyContact: string,
    public userEnrolled : boolean,
    public userFormNo : number,
    public guardianDeleteFlag: boolean,
    public guardianFirstName: string,
    public guardianLastName: string,
    public guardianPrimaryContact: string,
    public guardianSecondaryContact: string,
    public scheduleId : number
  ) {}
}
