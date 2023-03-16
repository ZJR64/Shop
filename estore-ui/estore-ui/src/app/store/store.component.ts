import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../product';
import { ProductService } from '../services/product.service'

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
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
    this.router.navigateByUrl(`admin/products/${product.id}`);
  }

  addNewProduct(): void {
    // Create Blank Product
    const newProduct: Product = {
      id: 0,
      name: 'name',
      type: 'Bean',
      description: 'description',
      price: 0.0,
      volume: 0.0
    };

    // to storage 
    this.productService.addProduct(newProduct)
    .subscribe(product => {
      // Get the updated list of products from the service
      this.productService.getProducts()
        .subscribe(products => {
          // Find the newly added product by name and type
          const matchingProducts = products.filter(
            i => i.name === newProduct.name && i.type === newProduct.type
          );
          if (matchingProducts.length > 0) {
            // Go to the detail screen for the newly added product
            this.goToProduct(matchingProducts[0]);
          }
        });
    });
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