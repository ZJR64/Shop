import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Product } from '../product';
import { ProductService } from '../services/product.service'

import { Ingredient } from '../ingredient';
import { IngredientService } from '../services/ingredients.service';
import { forkJoin, Observable } from 'rxjs';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {
  products!: Product[];
  ingredients!: Ingredient[];
  prices!: Map<String, number>;
  searchTerm?: string;
  sortOrder: string = '';
  sortReverse: boolean = false;

  constructor(
    private productService: ProductService,
    private ingredientService: IngredientService,
    private router: Router,
    ) { }

  ngOnInit(): void {
    this.prices = new Map<String, number>();
    this.getIngredients();
    this.getProducts();
  }

  getIngredients(): void {
    this.ingredientService.getIngredients()
      .subscribe((ingredients: Ingredient[]) => this.ingredients = ingredients);
  }

  getPrices(): void {
    this.products?.forEach((product: Product) => {
      this.prices.set(product.name, this.calcPrice(product));
    });
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe((products: Product[]) =>
      this.products = products
      );
    this.getPrices();
  }

  goToProduct(product: Product): void {
    this.router.navigateByUrl(`store/${product.id}`);
  }

  onSearch() {
    if (this.searchTerm) {
      this.productService.searchProducts(this.searchTerm).subscribe((data: any[]) => {
        this.products = data;
      });
    } else {
      this.getProducts();
    }
  }

  calcPrice(product: Product): number {
    if (!product) {
      return 0;
    }
    const size: number = 10;
    let price: number = 0;
    const keysArray = Object.keys(product.ingredients);
    const valuesArray = Object.values(product.ingredients);
    var i = -1;
    keysArray.forEach((name: String) => {
      i++;
      this.ingredients.forEach((ingredient: Ingredient) => {
        if (name == ingredient.name) {
          price += ingredient.price * valuesArray[i]; 
        }
      })
    });
  
    price = price * (1 + product.modPrice);
    price = price * size;
  
  
    return parseFloat(price.toFixed(2));;
  }

  sort(column: string) {
    if (this.sortOrder === column) {
      this.sortReverse = !this.sortReverse;
    } else {
      this.sortOrder = column;
      this.sortReverse = false;
    }
    this.products.sort((a, b) => {
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
}