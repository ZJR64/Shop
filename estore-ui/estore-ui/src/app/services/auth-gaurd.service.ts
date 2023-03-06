import { Injectable } from '@angular/core';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot} from '@angular/router';
import { LoginService } from './login.service';

@Injectable({ providedIn: 'root' })
export class AuthGuardService {

  constructor(
    private router: Router,
    private loginService: LoginService,
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.loginService.isLoggedIn$) {
      return true;
    } else {
      alert('Please log in')
      this.router.navigate(['/login']);
      return false;
    }
  }


}