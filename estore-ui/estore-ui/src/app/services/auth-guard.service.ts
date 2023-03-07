import { Injectable } from '@angular/core';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot} from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuardService {

  constructor(
    private router: Router,
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    //ensure user is logged in
    if(localStorage.getItem('currentUser')) {
      return true;
    }
    else {
      this.router.navigate(['/login']);
      return false;
    }
  }

}