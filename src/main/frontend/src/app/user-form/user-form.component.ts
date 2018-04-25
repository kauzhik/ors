import { Component } from '@angular/core';
import { User } from './shared/user';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/observable';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {
  public formModel: User;

  constructor(public http: Http) {
    this.formModel = new User(
      "", //firstName
      "", //lastName
      "", //email
      "", //username
      "", //password
      "ROLE_USER", //role
      false, //deleteflag person
      "", //mailingAddress
      "", //city
      "", //state
      "", //zipcode
      false, //deleteflag Address
      false, //userdeleteFlag
      "BOTH", //userContact
      true, //userContactNextSpring
      true, //userContactNextSummer
      "", //userEmergencyContact
      false, //userEnrolled
      1, //userFormNo
      false, //guardianDeleteFlag
      "", //guardianFirstName
      "", //guardianLastName
      "", //guardianPrimaryContact
      "", //guardianSecondaryContact
      0 //scheduleId
    );
  };

  instructorList: any;

  ngOnInit() {
    let headers = new Headers();
    headers.append('Authorization', 'save');
    console.log(headers);
    let options = new RequestOptions({headers: headers});
    let baseURL = "/v1/usuario/";
    console.log(options);
    this.http.get('http://192.168.43.204:8080/user/schedules/active', options)
    //.map(res => res.json());
      .map(this.mapData)
      .do(this.logData)
      .catch(this.catchError).subscribe(data => this.instructorList = data);
  }

  private mapData(res: Response) {
    return res.json();
  }

  private catchError(error: Response | any) {
    console.log(error);
    return Observable.throw(error.json().error || "server error.");

  }

  private logData(res: Response) {
    console.log(res);
  }

  submitInstructor() {
    var headers = new Headers();

    headers.append("Authorization", "save");
    let options = new RequestOptions({headers: headers});
    let instructorObject = {
      "firstName": this.formModel.firstName,
      "lastName": this.formModel.lastName,
      "email": this.formModel.email,
      "username": this.formModel.username,
      "password": this.formModel.password,
      "role": this.formModel.role,
      "deleteFlag": false,
      "addresses": [
        {
          "mailingAddress": this.formModel.mailingAddress,
          "city": this.formModel.city,
          "state": this.formModel.state,
          "zipCode": this.formModel.zipCode,
          "deleteFlag": false
        }
      ],
      "user": {
        "deleteFlag": false,
        "contact": "BOTH",
        "contactNextSpring": true,
        "contactNextSummer": true,
        "emergencyContact": this.formModel.userEmergencyContact,
        "enrolled": false,
        "formNo": 1,
        "guardian": {
          "deleteFlag": false,
          "firstName": this.formModel.guardianFirstName,
          "lastName": this.formModel.guardianLastName,
          "primaryContact": this.formModel.guardianPrimaryContact,
          "secondaryContact": this.formModel.guardianSecondaryContact
        },
        "schedule": {
          "id": this.formModel.scheduleId
        }

      }
    }
    let instructorObject2 = {
      "username": "jack",
      "password": "jack"
    }
    var headers2 = new Headers();
    headers2.append("Content-Type", "application/json");
    let options2 = new RequestOptions({headers: headers2});
    console.log(instructorObject);

    this.sent(instructorObject, options);
  }

  sent(instructorObject, options) {
    this.http.put('http://192.168.43.204:8080/user/save', instructorObject, options)
      .subscribe(res => {
        if (res.status === 200) {
          console.log('Response: ', res);
        }
      }, error => {
        if (error.status === 401) {
          console.log('Error');
        }
      });
  }
}
