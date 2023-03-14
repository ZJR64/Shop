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
  @Input() ingredients?: Map<String, number>;


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
    this.getIngredients();

    this.getProduct();
  }

  getIngredients(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getIngredients(id).subscribe(ingredients => this.ingredients = ingredients)
  }

  getProduct(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  addIngredient(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.ingredients) {
      this.productService.getIngredients(id).subscribe(ingredients => this.ingredients = ingredients);
      this.ingredients.set((<HTMLInputElement>document.getElementById("iname")).value, parseFloat((<HTMLInputElement>document.getElementById("ivolume")).value));
      this.ingredients.forEach((value: number, key: String) => {
        console.log(key + " " + value);
      })

      console.log(this.product?.ingredients);
      this.productService.updateIngredients(this.ingredients)
        .subscribe(() => this.goBack());
    }
  }
}
