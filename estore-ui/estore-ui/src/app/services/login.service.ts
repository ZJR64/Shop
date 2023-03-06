import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  public isLoggedIn$: Observable<boolean>;
  private _sourceUserLoggedIn = new BehaviorSubject<boolean>(false);

  constructor() {
    this.isLoggedIn$ = this._sourceUserLoggedIn.asObservable();
  }

  setLoggedIn(value: boolean): void {
    this._sourceUserLoggedIn.next(value);
  }
}
