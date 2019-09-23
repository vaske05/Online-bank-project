import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import {HttpParams} from "@angular/common/http";

//import {Observable} from 'rxjs/Observable';

@Injectable()
export class LoginService {

  constructor( private http: Http ) { }

  sendCredential(username: string, password: string) {
    let url = "http://localhost:8080/admin";
    let params = 'username=' + username + '&password=' + password;

    let body = new HttpParams();
    body = body.set('username', username);
    body = body.set('password', password);

    
    let headers = new Headers(
      {
        'Content-Type': 'application/x-form-urlencoded',
         'Access-Control-Alow-Credentials' : true,
         'Access-Control-Allow-Origin': true
      });
      return this.http.post(url, params, { headers: headers, withCredentials: true });
  }

  logout() {
    let url = "http://localhost:8080/logout";
    return this.http.get(url, { withCredentials: true });
  }

}
