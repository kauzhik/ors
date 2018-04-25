// import { Component, OnInit } from '@angular/core';
import {Component, OnInit} from '@angular/core';
import { User } from './shared/user';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-instructor-form',
  templateUrl: './instructor-form.component.html',
  styleUrls: ['./instructor-form.component.css']
})
export class InstructorFormComponent{
  public formModel: User;

  constructor(public http:Http) {
    this.formModel = new User(
      "", //firstName
      "", //lastName
      "", //email
      "", //username
      "", //password
      "ROLE_INSTRUCTOR", //role
      false, //deleteflag person
      "", //mailingAddress
      "", //city
      "", //state
      "", //zipcode
      false //deleteflag Address
    );
  };
  submitInstructor() {
    var headers = new Headers();

    headers.append("Authorization","save");
    let options = new RequestOptions({ headers: headers });
    //console.log("Submitting!", this.formModel);
    let instructorObject = {
      "firstName": this.formModel.firstName,
      "lastName": this.formModel.lastName,
      "email": this.formModel.email,
      "username": this.formModel.username,
      "password": this.formModel.password,
      "role": this.formModel.role,
      "deleteFlag": this.formModel.deleteFlag,
      "addresses": [
        {
          "mailingAddress": this.formModel.mailingAddress,
          "city": this.formModel.city,
          "state": this.formModel.state,
          "zipCode": this.formModel.zipCode,
          "deleteFlag": this.formModel.deleteFlagAddress
        }
      ]
    }

    let instructorObject2 = {
      "username":"jack",
      "password":"jack"
    }
    var headers2 = new Headers();

    headers2.append("Content-Type","application/json");
    let options2 = new RequestOptions({ headers: headers2 });
    console.log(instructorObject);

    this.sent(instructorObject,options);
  }
  sent(instructorObject,options){
    this.http.put('http://192.168.43.204:8080/instructor/save',instructorObject,options)
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
  private mapData(res: Response){
    return res.json();
  }

  private catchError(error: Response|any){
    console.log(error);
    return Observable.throw(error.json().error || "server error.");

  }

  private logData(res: Response){
    console.log(res);
  }
}
