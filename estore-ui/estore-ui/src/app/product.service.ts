import { Injectable } from '@angular/core';
import { Product } from './product';
import { PRODUCTS } from './mock-products';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private messageService: MessageService) { }

  getProducts(): Observable<Product[]> {
    const products = of(PRODUCTS);
    this.messageService.add('ProductService: fetched products');
    return products;
  }

  getProduct(id: number): Observable<Product> {
    // For now, assume that a hero with the specified `id` always exists.
    // Error handling will be added in the next step of the tutorial.
    const product = PRODUCTS.find(p => p.id === id)!;
    this.messageService.add(`ProductService: fetched product id=${id}`);
    return of(product);
  }
}
