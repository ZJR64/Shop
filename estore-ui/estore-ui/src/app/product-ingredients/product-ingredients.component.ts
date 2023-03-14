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
    console.log(this.product);
  }


  getProduct(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  addIngredient(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.product) {
      const i = new Map<String, number>(Object.entries(this.product['ingredients']))
      i.set((<HTMLInputElement>document.getElementById("iname")).value, parseFloat((<HTMLInputElement>document.getElementById("ivolume")).value));
      console.log(i);
      const i2 = Object.fromEntries(i);
      Object.assign(this.product.ingredients, i2);
      console.log(this.product);
      this.productService.updateProduct(this.product).subscribe();
    }
  }
}
