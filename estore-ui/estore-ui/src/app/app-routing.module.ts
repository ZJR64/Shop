import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductsComponent } from './products/products.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductIngredientsComponent } from './product-ingredients/product-ingredients.component';

const routes: Routes = [
  { path: 'products', component: ProductsComponent },
  { path: 'detail/:id', component: ProductDetailComponent },
  { path: 'detail/:id/ingredients', component: ProductIngredientsComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }