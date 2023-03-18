import { Component, Input } from '@angular/core';
import { Product } from '../product';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { MessageService } from '../message.service';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-ingredients',
  templateUrl: './product-ingredients.component.html',
  styleUrls: ['./product-ingredients.component.css']
})

export class ProductIngredientsComponent {

  private log(message: String) {
    this.messageService.add(`ProductService: ${message}`);
  }
  @Input() product?: Product;


  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location,
    private messageService: MessageService
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

  deleteIngredient(key: String): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.product) {
      const i = new Map<String, number>(Object.entries(this.product['ingredients']))
      i.delete(key);
      const i2 = Object.fromEntries(i);
      this.product['ingredients'] = i2;
    }
  }

  addIngredient(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.product) {
      var i = new Map<String, number>();
      if (this.product['ingredients'] != null) {
        i = new Map<String, number>(Object.entries(this.product['ingredients']))
      }
      i.set((<HTMLInputElement>document.getElementById("iname")).value, parseFloat((<HTMLInputElement>document.getElementById("ivolume")).value));
      const i2 = Object.fromEntries(i);
      this.product['ingredients'] = i2;
    }
  }
}
