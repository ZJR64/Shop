import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { User } from '../user';
import { UserService } from '../services/user.service';


import { ProductService } from '../product.service';
import { Product } from '../product';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent {
  @Input() product?: Product;
  @Input() user?: User;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,

    private productService: ProductService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.getProduct();
    this.getUser();
  }

  getProduct(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  goBack(): void {
    this.location.back();
  }

  getUser(): void {
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(user => this.user = user);
  }

  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product)
        .subscribe(() => this.goBack());
    }
    if (this.user) {
      this.userService.updateUser(this.user).subscribe(() => this.goBack());
    }
  }

  addToCart(key: String): void {
    if (this.user && this.product) {
      const i = new Map<String, String[]>(Object.entries(this.user['cart']))
      var num = 0;
      if (i.has(key)) {
        num = Number(i.get(key)?.at(2));
      }
      num++;
      console.log(i);
      i.set(key, [this.product.name, "large", "" + num]);
      console.log(i);
      const i2 = Object.fromEntries(i);
      this.user['cart'] = i2;
      this.save();
    }
  }
}
