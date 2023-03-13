import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Ingredient } from '../ingredient';
import { IngredientService } from '../services/ingredients.service'

@Component({
  selector: 'app-ingredients',
  templateUrl: './ingredients.component.html',
  styleUrls: ['./ingredients.component.css']
})
export class IngredientsComponent implements OnInit {
  ingredients!: Ingredient[];
  searchTerm?: string;

  constructor(
    private ingredientService: IngredientService,
    private router: Router,
    ) { }

  ngOnInit(): void {
    this.getIngredients();
  }

  getIngredients(): void {
    this.ingredientService.getIngredients()
      .subscribe((ingredients: Ingredient[]) => this.ingredients = ingredients);
  }

  goToIngredient(ingredient: Ingredient): void {
    this.router.navigateByUrl(`/ingredients/${ingredient.id}`);
  }

  addNewIngredient(): void {
    //Create a new ingredient
  }

  onSearch() {
    if (this.searchTerm) {
      this.ingredientService.searchIngredients(this.searchTerm).subscribe((data: any[]) => {
        this.ingredients = data;
      });
    } else {
      this.getIngredients();
    }
  }
}