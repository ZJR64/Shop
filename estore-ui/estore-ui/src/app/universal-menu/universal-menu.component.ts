import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { Location } from '@angular/common';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-universal-menu',
  templateUrl: './universal-menu.component.html',
  styleUrls: ['./universal-menu.component.css']
})
export class UniversalMenuComponent {

  constructor(
    private router: Router,
    private userService: UserService,
    private location: Location
    ) {}

  goBack(): void {
    this.location.back();
  }

  goLogout(): void {
    this.router.navigateByUrl('/logout');
  }

  goSettings(): void {
    this.router.navigateByUrl('/settings');
  }
}
