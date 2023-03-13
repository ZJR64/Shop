import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ingredient } from '../ingredient';
import { IngredientService } from '../services/ingredients.service';

@Component({
  selector: 'app-ingredient-details',
  templateUrl: './ingredient-details.component.html',
  styleUrls: ['./ingredient-details.component.css']
})
export class IngredientDetailsComponent implements OnInit {
  ingredient!: Ingredient;

  constructor(private route: ActivatedRoute, private ingredientService: IngredientService) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id')!;

    this.ingredientService.getIngredient(id).subscribe(
      (ingredient) => {
        this.ingredient = ingredient;
      });
  }

  saveIngredient(): void {
    if (this.ingredient) {
      this.ingredientService.updateIngredient(this.ingredient)
    }
  }

}