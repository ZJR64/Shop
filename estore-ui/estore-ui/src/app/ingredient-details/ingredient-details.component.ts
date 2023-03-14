import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Ingredient } from '../ingredient';
import { IngredientService } from '../services/ingredients.service';

@Component({
  selector: 'app-ingredient-details',
  templateUrl: './ingredient-details.component.html',
  styleUrls: ['./ingredient-details.component.css']
})
export class IngredientDetailsComponent implements OnInit {
  ingredient!: Ingredient;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private ingredientService: IngredientService) {
    const id = +this.route.snapshot.paramMap.get('id')!;

    this.ingredientService.getIngredient(id).subscribe(
      (ingredient) => {
        this.ingredient = ingredient;
      });
     }

  ngOnInit() {
    console.log("opened details")
  }

  addToVolume(amount: string): void {
    this.ingredient.volume += parseFloat(amount);
  }

  saveIngredient(): void {
    this.ingredientService.updateIngredient(this.ingredient)
    .subscribe(() => {
      console.log('Ingredient saved');
    });
  }

  deleteIngredient(): void {
    this.ingredientService.deleteIngredient(this.ingredient.id)
    .subscribe(() => {
        console.log(`Deleted ingredient with id=${this.ingredient.id}`);
        this.router.navigateByUrl(`admin/ingredients`);
    });
  }

}