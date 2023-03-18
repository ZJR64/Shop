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

  getUser(): void {
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(user => this.user = user);
  }

  delete(key: String, index: number): void {
    if (this.user) {
      const values: number[] = this.user.cart.get(key) || [];
      values.splice(index, 2); // remove the value at the specified index
      this.user.cart.set(key, values);
      this.userService.updateUser(this.user).subscribe();
    }
  }
}
