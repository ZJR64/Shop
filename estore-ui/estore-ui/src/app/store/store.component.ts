import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../product';
import { ProductService } from '../services/product.service'

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {
  products!: Product[];
  searchTerm?: string;
  sortOrder: string = '';
  sortReverse: boolean = false;

  constructor(
    private productService: ProductService,
    private router: Router,
    ) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe((products: Product[]) => this.products = products);
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