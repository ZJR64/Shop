import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuardService } from './services/auth-guard.service';
import { SafeGuardService } from './services/safe-guard.service';

import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { MenuComponent } from './menu/menu.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductIngredientsComponent } from './product-ingredients/product-ingredients.component';
import { ProductsComponent } from './products/products.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';

const routes: Routes = [
    { path: '', component: MenuComponent, children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent, canActivate: [AuthGuardService]},

      { path: 'products', component: ProductsComponent },
      { path: 'detail/:id', component: ProductDetailComponent },
      { path: 'detail/:id/ingredients', component: ProductIngredientsComponent },
      { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent }
    ],
  },
    { path: 'login', component: LoginComponent, canActivate: [SafeGuardService] },
    { path: 'register', component: RegisterComponent, canActivate: [SafeGuardService] },
    { path: 'logout', component: LogoutComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }