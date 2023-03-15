import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {
  isAdmin?: boolean;

  constructor(
    private router: Router,
    private userService: UserService,
    ) {
      this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(
        (user) => {
          this.isAdmin = user.admin;
        });
    }

  goHome(): void {
    this.router.navigateByUrl('/home');
  }

  goLogout(): void {
    this.router.navigateByUrl('/logout');
  }

  goAdmin(): void {
    this.router.navigateByUrl('/admin');
  }

  goDashboard(): void {
    this.router.navigateByUrl('/dashboard');
  }
  
}
