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
    private ingredientService: IngredientService) { }

  ngOnInit() {
    console.log("opened details")
    const id = +this.route.snapshot.paramMap.get('id')!;

    this.ingredientService.getIngredient(id).subscribe(
      (ingredient) => {
        this.ingredient = ingredient;
      });
  }

  saveIngredient(): void {
    this.ingredientService.updateIngredient(this.ingredient)
  }

  deleteIngredient(): void {
    this.ingredientService.deleteIngredient(this.ingredient.id)
    this.router.navigateByUrl(`admin/ingredients`);
  }

}