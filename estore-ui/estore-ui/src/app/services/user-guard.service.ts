import { Injectable } from '@angular/core';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot} from '@angular/router';

import { UserService } from '../services/user.service';

@Injectable({ providedIn: 'root' })
export class UserGuardService {
  isAdmin?: boolean;

  constructor(
    private router: Router,
    private userService: UserService,
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    //ensure user is not an admin
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(
      (user) => {
        if (user && user.admin) {
          this.isAdmin = true;
        }
        else {
          this.isAdmin = false;
        }

        if (this.isAdmin) {
          this.router.navigate(['/admin']);
        }
      });
      return !this.isAdmin!;
  }

}