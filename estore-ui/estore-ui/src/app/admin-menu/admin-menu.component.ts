import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css']
})
export class AdminMenuComponent {

  constructor(
    private router: Router,
  ) { }

  goHome(): void {
    this.router.navigateByUrl('/home');
  }

  goAdmin(): void {
    this.router.navigateByUrl('/admin');
  }

  goIngredients(): void {
    this.router.navigateByUrl('/admin/ingredients');
  }

  goAdmins(): void {
    this.router.navigateByUrl('/admin/admins');
  }

  goProducts(): void {
    this.router.navigateByUrl('/admin/products');
  }

  goCart(): void {
    this.router.navigateByUrl('/cart');
  }
}
