import { Injectable } from '@angular/core';
import { User } from '../interfaces/user.interface';
import { BehaviorSubject, Observable } from 'rxjs';
import { Topics } from '../interfaces/topics.interface';
import { Router, RouterEvent, Routes } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserSessionService {

  constructor(
    private router: Router
    
  ) { }
  
  public isLogged = false;
  public subscriptions: Topics[] = [];
  public user: User = {} as User;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  private subscriptionsSubject = new BehaviorSubject<Topics[]>(this.subscriptions);
  private userInformationSubject = new BehaviorSubject<User>(this.user);
  

  public setSubscriptions(subscriptions: Topics[]): void {
    this.subscriptions = subscriptions;
    this.subscriptions.sort((a, b) => a.title.localeCompare(b.title));
    this.subscriptionsSubject.next(this.subscriptions);
  }

  public setUserInformation(user: User): void {
    this.user = user;
    this.userInformationSubject.next(this.user);
  }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public $subscriptions(): Observable<Topics[]> {
    return this.subscriptionsSubject.asObservable();
  }

  public logIn(token: string): void {
    localStorage.setItem('token', token);
    this.isLogged = true;
    console.log('User logged in:', this.isLogged);
    this.next();
  }

  public logout(): void {
    localStorage.removeItem('token');
    this.setUserInformation({} as User);
    this.setSubscriptions([]);
    this.isLogged = false;
    this.isLoggedSubject.next(this.isLogged);
    this.router.navigate(['/login']);
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
