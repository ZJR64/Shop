import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css']
})
export class AdminMenuComponent {

  constructor(
    private router: Router,
    private userService: UserService,
    ) {}

  goHome(): void {
    this.router.navigateByUrl('/home');
  }
}
