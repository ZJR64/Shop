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
    this.router.navigateByUrl(`admin/ingredients/${ingredient.id}`);
  }

  addNewIngredient(): void {
    // Create Blank Ingredient
    const newIngredient: Ingredient = {
      id: 0,
      name: 'name',
      type: 'Bean',
      description: 'description',
      price: 0.0,
      volume: 0.0
    };

    // to storage 
    this.ingredientService.addIngredient(newIngredient)
    .subscribe(ingredient => {
      // Get the updated list of ingredients from the service
      this.ingredientService.getIngredients()
        .subscribe(ingredients => {
          // Find the newly added ingredient by name and type
          const matchingIngredients = ingredients.filter(
            i => i.name === newIngredient.name && i.type === newIngredient.type
          );
          if (matchingIngredients.length > 0) {
            // Go to the detail screen for the newly added ingredient
            this.goToIngredient(matchingIngredients[0]);
          }
        });
    });
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