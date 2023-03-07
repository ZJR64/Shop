import { Injectable } from '@angular/core';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot} from '@angular/router';
import { map, Observable } from 'rxjs';
import { LoginService } from './login.service';

@Injectable({ providedIn: 'root' })
export class AuthGuardService {

  constructor(
    private router: Router,
    private loginService: LoginService,
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.loginService.isLoggedIn$.pipe(
      map(isLoggedIn => {
        if (isLoggedIn) {
          return true;
        } else {
          this.router.navigate(['/login']);
          return false;
        }
      })
    );
  }
  
}