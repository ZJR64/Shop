import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SafeGuardService {

  constructor(
    private router: Router,
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    //ensure user is not logged in
    if(localStorage.getItem('currentUser')) {
      this.router.navigate(['']);
      return false;
    }
    else {
      return true;
    }
  }
}
