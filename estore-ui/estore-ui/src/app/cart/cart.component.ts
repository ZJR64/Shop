import { User } from '../user';

import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { UserService } from '../services/user.service';
import { ProductService } from '../services/product.service';
import { IngredientService } from '../services/ingredients.service';
import { Ingredient } from '../ingredient';
import { Product } from '../product';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  user!: User;
  products!: Map<string, number[]>;

  constructor(
    private userService: UserService,
    private ingredientService: IngredientService,
    private productService: ProductService,
    private router: Router,
  ) {
    this.getUser();

  }

  ngOnInit() {
    console.log("opened cart")
    this.products = new Map<string, number[]>();
  }

  getUser(): void {
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(user => {
      this.user = user
      const keysArray = Object.keys(user.cart);
      const valuesArray = Object.values(user.cart);
      for (let i = 0; i < keysArray.length; i++) {
        this.products.set(keysArray[i], valuesArray[i]);
      }
    });
  }

  returnIngredients(productName: string, size: number): void {
    this.productService.getProducts().subscribe((products: Product[]) => {
      products.forEach((product: Product) => {
        if (product.name == productName) {
          const keysArray = Object.keys(product.ingredients);
          const valuesArray = Object.values(product.ingredients);
          var i = -1;
          keysArray.forEach((name: String) => {
            i++;
            this.ingredientService.getIngredients().subscribe((ingredients: Ingredient[]) => {
              ingredients.forEach((ingredient: Ingredient) => {
                if (name == ingredient.name) {
                  ingredient.volume += size * valuesArray[i];
                  this.ingredientService.updateIngredient(ingredient).subscribe();
                }
              })
            });
          });
        }
      })
    });
  }

  calcTotal(): number {
    var price: number = 0;
    this.products.forEach((details: number[]) => {
      var index: number = -1;
      details.forEach((value: number) => {
        index++;
        if (index%2 == 1) {
          price += value;
        }
      });
    });

    return price;
  }

  delete(key: string, index: number): void {
    if (this.user) {
      var values: number[] = this.products.get(key)!;

      // add ingredients back to storage
      this.returnIngredients(key, values[index]);

      // update cart
      values.splice(index, 2);
      if (values.length != 0) {
        this.products.set(key, values);
      }
      else {
        this.products.delete(key);
      }
      this.user.cart = Object.fromEntries(this.products.entries());
      this.userService.updateUser(this.user).subscribe();

    }
  }

  checkout(): void {
    this.router.navigateByUrl('/checkout');
  }
}
