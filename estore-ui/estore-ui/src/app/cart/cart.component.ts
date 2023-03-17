import { User } from '../user';
import { Product } from '../product';

import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { UserService } from '../services/user.service';
import { ProductService } from '../product.service';
import { take } from 'rxjs';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  @Input() user?: User;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private productService: ProductService,
    private location: Location
  ) {
    this.getUser();

  }

  ngOnInit() {
    console.log("opened cart")
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.user) {
      this.userService.updateUser(this.user)
        .subscribe(() => this.goBack());
    }
  }

  getUser(): void {
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(user => this.user = user);
  }

  delete(key: number): void {
    if (this.user) {
      const i = new Map<String, String[]>(Object.entries(this.user['cart']))
      i.delete("" + key);
      const i2 = Object.fromEntries(i);
      this.user['cart'] = i2;
    }
  }
}
