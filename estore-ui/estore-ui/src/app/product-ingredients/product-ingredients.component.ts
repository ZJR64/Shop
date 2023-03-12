import { Component, Input } from '@angular/core';
import { Product } from '../product';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-ingredients',
  templateUrl: './product-ingredients.component.html',
  styleUrls: ['./product-ingredients.component.css']
})
export class ProductIngredientsComponent {
  @Input() product?: Product;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location
  ) { }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product)
        .subscribe(() => this.goBack());
    }
  }

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }
}
