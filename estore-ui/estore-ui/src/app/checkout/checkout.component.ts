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

  order(): void {
    //TODO create a new order
    // Create Test Ingredient
    const newProducts = {
      "Pint o Pinto": [8.0, 12.0],
      "Royal Blend": [32.0]
    };
    const newOrder: Order = {
      id: 0,
      email: 'test@attempt.com',
      address: '123 Sesame Street',
      payment: '1234-5678-9012-3456',
      price: 50.67,
      products: newProducts,
      fulfilled: false
    };

    // to storage 
    this.orderService.addOrder(newOrder)
    .subscribe(order => {
      console.log("test order made and stored")
    });
    // clear cart
    this.user.cart = Object.fromEntries(new Map<string, number[]>());
    this.userService.updateUser(this.user).subscribe();
    this.router.navigateByUrl('/home');
  }
}
