import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {AuthSuccess} from "../interfaces/AuthResponse";
import {RegisterRequest} from "../interfaces/RegisterRequest";
import {Injectable} from '@angular/core';
import {LoginRequest} from "../interfaces/LoginRequest";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // private pathService = 'api/auth';
  private pathService = 'http://localhost:8080/api/auth';

  constructor(private httpClient: HttpClient) { }


  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/login`, loginRequest);
  }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/register`, registerRequest);
  }
}
