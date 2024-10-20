import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = 'http://localhost:8080/api';

  constructor(private httpClient: HttpClient) { }

  public getUserById(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService +'/user'}/${id}`);
  }

  public getUserMe(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService + '/auth/me'}`);
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete(`${this.pathService}/${id}`);
  }

  public updateMe(data: any) {
    return this.httpClient.put<User>(`${this.pathService + '/user/me'}`, data);
    console.log(data);
  }
  

}
