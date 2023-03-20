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
  price!: number;

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

  calcTotal(): void {
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe((user) => {
      var price: number = 0;
      var products: Map<string, number[]> = new Map<string, number[]>();
      const keysArray = Object.keys(user.cart);
      const valuesArray = Object.values(user.cart);
      for (let i = 0; i < keysArray.length; i++) {
        products.set(keysArray[i], valuesArray[i]);
      }

      products.forEach((details: number[]) => {
        var index: number = -1;
        details.forEach((value: number) => {
          index++;
          if (index%2 == 1) {
            price += value;
          }
        });
      });

      this.price = price;
    });
  }


  toggleCart(): void {
    this.calcTotal();
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

  goCheckout(): void {
    this.router.navigateByUrl('/checkout');
  }
}
