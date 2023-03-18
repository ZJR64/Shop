import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from '../order';
import { OrderService } from '../services/order.service';



@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent {
  orders!: Order[];
  orderView!: boolean;
  searchTerm?: string;
  sortOrder!: string;
  sortReverse!: boolean;

  constructor(
    private orderService: OrderService,
    private router: Router,
    ) { }

  ngOnInit(): void {
    this.orderView = true;
    this.sortReverse = false;
    this.sortOrder = '';
    this.getOrders();
  }

  getOrders(): void {
    if (this.orderView) {
      this.getUnfulfilledOrders();
    }
    else {
      this.getFulfilledOrders();
    }
  }

  getFulfilledOrders(): void {
    this.orderService.getOrders()
      .subscribe((orders: Order[]) =>
      this.orders = orders.filter(order => order.fulfilled)
      );
  }

  getUnfulfilledOrders(): void {
    this.orderService.getOrders()
      .subscribe((orders: Order[]) =>
      this.orders = orders.filter(order => !order.fulfilled)
      );
  }

  onSearch() {
    if (this.searchTerm) {
      this.orderService.searchOrders(this.searchTerm).subscribe((data: any[]) => {
        this.orders = data;
      });
    } else {
      this.getOrders();
    }
  }

  switch():void {
    this.orderView = !this.orderView;
    this.getOrders();
  }

  changeFullfilled(order: Order): void {
    order.fulfilled = !order.fulfilled;
    this.orderService.updateOrder(order)
    .subscribe(() => {
      console.log('Order saved');
      this.getOrders();
    });
  }

  sort(column: string) {
    if (this.sortOrder === column) {
      this.sortReverse = !this.sortReverse;
    } else {
      this.sortOrder = column;
      this.sortReverse = false;
    }
    this.orders.sort((a, b) => {
      if (this.sortReverse) {
        [a, b] = [b, a];
      }
      if (a[column] < b[column]) {
        return -1;
      }
      if (a[column] > b[column]) {
        return 1;
      }
      return 0;
    });
  }

  test() {
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
  }
}
