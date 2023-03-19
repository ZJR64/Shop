import { User } from '../user';

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Order } from '../order';
import { OrderService } from '../services/order.service';


@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {
  user!: User;
  price!: number;
  payment?: string;

  constructor(
    private userService: UserService,
    private router: Router,
    private orderService: OrderService,
  ) {
    this.getUser();
    this.calcTotal();
  }

  ngOnInit() {
    console.log("opened cart");
  }

  getUser(): void {
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(user => {
      this.user = user
    });
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

  isOrder(): boolean {
    if (this.price > 0) {
      return true;
    }
    return false;
  }

  order(): void {
    //check that payment and address are not null
    if(!this.payment) {
      alert("You have not selected a payment method");
      return;
    }
    if(!this.user.address) {
      alert("Please go to user settings and add your address");
      return;
    }
    // Create Ingredient
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe((user) => {
      var products: Map<string, number[]> = new Map<string, number[]>();
      const keysArray = Object.keys(user.cart);
      const valuesArray = Object.values(user.cart);
      for (let i = 0; i < keysArray.length; i++) {
        products.set(keysArray[i], valuesArray[i]);
      }

      var newProducts: {
        [key: string]: number[];
      }={};

      products.forEach((details: number[], key: string) => {
        const volumes: number[] = [];
        for (let i = 0; i < details.length; i += 2) {
          volumes.push(details[i]);
        }
        newProducts[key] = volumes;
      });

      const newOrder: Order = {
        id: 0,
        email: user.email,
        address: user.address,
        payment: this.payment!,
        price: this.price,
        products: newProducts!,
        fulfilled: false
      }
      // to storage 
      this.orderService.addOrder(newOrder)
      .subscribe(order => {
        console.log("new order made and stored")
      });

      // clear cart
      this.user.cart = Object.fromEntries(new Map<string, number[]>());
      this.userService.updateUser(this.user).subscribe();
      this.router.navigateByUrl('/home');
    });
  }
}
