import { User } from '../user';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { UserService } from '../services/user.service';
import { ProductService } from '../services/product.service';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  @Input() user?: User;
  products!: Map<String, number[]>;

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
    this.products = new Map<String, number[]>();
  }

  getUser(): void {
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(user => {
      this.user = user
      const keysArray = Object.keys(user.cart);
      const valuesArray = Object.values(user.cart);
      for (let i = 0; i < keysArray.length; i++) {
        this.products.set(keysArray[i], valuesArray[i]);
      }
    });
  }

  delete(key: String, index: number): void {
    if (this.user) {
      var values: number[] = this.products.get(key)!;
      values.splice(index, 2);
      this.products.set(key, values);
      this.user.cart = this.products;
      //this.userService.updateUser(this.user).subscribe();
    }
  }
}
