import { User } from '../user';
import { Product } from '../product';

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { UserService } from '../services/user.service';
import { ProductService } from '../product.service';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  user!: User;
  product!: Product;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private productService: ProductService,
    private location: Location
  ) {

    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(
      (user) => {
        this.user = user;
      });
  }

  ngOnInit() {
    console.log("opened cart")
  }

  findProduct(id: number): String {
    this.getProduct(id);
    return this.product.name;
  }

  getProduct(id: number): void {
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }
}
