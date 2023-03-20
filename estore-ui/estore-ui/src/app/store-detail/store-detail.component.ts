import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Ingredient } from '../ingredient';
import { IngredientService } from '../services/ingredients.service';

import { Product } from '../product';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-store-detail',
  templateUrl: './store-detail.component.html',
  styleUrls: ['./store-detail.component.css']
})
export class StoreDetailComponent {
  product!: Product;
  price!: number;
  size!: number;
  ingredients!: Ingredient[];

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private ingredientService: IngredientService,
    private userService: UserService,
    ) {
    const id = +this.route.snapshot.paramMap.get('id')!;

    this.productService.getProduct(id).subscribe(
      (product: Product) => {
        this.product = product;
      });
     }

  ngOnInit() {
    this.getIngredients();
    this.changeSize(10);
    this.calcPrice();
  }

  getIngredients(): void {
    this.ingredientService.getIngredients()
      .subscribe((ingredients: Ingredient[]) => {
        this.ingredients = ingredients;
        this.calcPrice();}
      );
  }

  calcPrice(): void {
    if (!this.product) {
      return;
    }
    let price: number = 0;
    const keysArray = Object.keys(this.product.ingredients);
    const valuesArray = Object.values(this.product.ingredients);
    var i = -1;
    keysArray.forEach((name: String) => {
      i++;
      this.ingredients.forEach((ingredient: Ingredient) => {
        if (name == ingredient.name) {
          price += ingredient.price * valuesArray[i]; 
        }
      })
    });
  
    price = price * (1 + this.product.modPrice);
    price = price * this.size;
  
  
    this.price = parseFloat(price.toFixed(2));
  }

  changeSize(size: number) {
    this.size = size;
    this.calcPrice();
  }

  canMakeProduct(size: number): boolean {
    const keysArray = Object.keys(this.product.ingredients);
    const valuesArray = Object.values(this.product.ingredients);
    let canMake = true;
    if (this.ingredients && this.product) {
      let i = -1;
      keysArray!.forEach((name: String) => {
        i++;
        this.ingredients.forEach((ingredient: Ingredient) => {
          if (name == ingredient.name && ingredient.volume < size * valuesArray[i]) {
            canMake = false;
          }
        })
      });
    }
    return canMake;
  }

  removeIngredients(): void {
    const keysArray = Object.keys(this.product.ingredients);
    const valuesArray = Object.values(this.product.ingredients);
    var i = -1;
    keysArray.forEach((name: String) => {
      i++;
      this.ingredients.forEach((ingredient: Ingredient) => {
        if (name == ingredient.name) {
          ingredient.volume -= this.size * valuesArray[i];
          this.ingredientService.updateIngredient(ingredient).subscribe();
        }
      })
    });
  }

  addToCart(): void {
    if (this.canMakeProduct(this.size)) {
      this.removeIngredients();

      // Add the product to the cart
      var products: Map<String, number[]> = new Map<String, number[]>();
      this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(user => {
        var added: boolean = false;
        if (user.cart) {
          const keysArray = Object.keys(user.cart);
          const valuesArray = Object.values(user.cart);
          for (let i = 0; i < keysArray.length; i++) {
            if (keysArray[i] == this.product.name) {
              valuesArray[i].push(this.size);
              valuesArray[i].push(this.price);
              added = true;
            }
          products.set(keysArray[i], valuesArray[i]);
          }
        }

        if (!added) {
          products.set(this.product.name, [this.size, this.price])
        }

        user.cart = Object.fromEntries(products.entries());
        this.userService.updateUser(user).subscribe();
      });
    } else {
      alert("Not enough ingredients to make this product!");
    }
  }

}
