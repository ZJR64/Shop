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
  showSidebar!: boolean;

  constructor(
    private router: Router,
    private userService: UserService,
  ) {
    document.addEventListener('click', this.handleClickOutside.bind(this));
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(
      (user) => {
        this.isAdmin = user.admin;
      });
      this.showSidebar = false;
  }

  handleClickOutside(event: MouseEvent) {
    const cartButton = document.querySelector('.cart-button');
    const cartSidebar = document.querySelector('.cart-sidebar');
  
    // check if click occurred outside of cart button and sidebar
    if (cartButton && cartSidebar && !cartButton.contains(event.target as Node) && !cartSidebar.contains(event.target as Node)) {
      this.showSidebar = false;
    }
  }

  toggleCart(): void {
    this.showSidebar = !this.showSidebar;
  }

  goHome(): void {
    this.router.navigateByUrl('/home');
  }

  goAdmin(): void {
    this.router.navigateByUrl('/admin');
  }

  goDashboard(): void {
    this.router.navigateByUrl('/dashboard');
  }

  goStore(): void {
    this.router.navigateByUrl('/store');
  }
}
