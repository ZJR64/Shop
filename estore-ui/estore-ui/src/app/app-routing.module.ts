import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuardService } from './services/auth-guard.service';
import { SafeGuardService } from './services/safe-guard.service';
import { AdminGuardService } from './services/admin-guard.service';

import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { RegisterComponent } from './register/register.component';

import { UniversalMenuComponent } from './universal-menu/universal-menu.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { MenuComponent } from './menu/menu.component';

import { HomeComponent } from './home/home.component';
import { AdminMenuComponent } from './admin-menu/admin-menu.component';
import { IngredientsComponent } from './ingredients/ingredients.component';
import { IngredientDetailsComponent } from './ingredient-details/ingredient-details.component';
import { AdminsComponent } from './admins/admins.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductIngredientsComponent } from './product-ingredients/product-ingredients.component';
import { ProductsComponent } from './products/products.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { UserSettingsComponent } from './user-settings/user-settings.component';
import { OrdersComponent } from './orders/orders.component';
import { DeleteAccountComponent } from './delete-account/delete-account.component';

const routes: Routes = [
  { path: '', component: UniversalMenuComponent, canActivate: [AuthGuardService], children: [
    { path: '', component: MenuComponent, children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent},
      { path: 'dashboard', component: DashboardComponent },
    ]},
    
    { path: 'admin', component: AdminMenuComponent, canActivate: [AdminGuardService], children: [
      { path: '', component: AdminHomeComponent},
      { path: 'ingredients', component: IngredientsComponent},
      { path: 'ingredients/:id', component: IngredientDetailsComponent },
      { path: 'orders', component: OrdersComponent},
      { path: 'admins', component: AdminsComponent},
      { path: 'products', component: ProductsComponent },
      { path: 'products/:id', component: ProductDetailComponent },
      { path: 'products/:id/ingredients', component: ProductIngredientsComponent },
    ]},

    { path: 'settings', component: UserSettingsComponent },
    { path: 'logout', component: LogoutComponent },
    { path: 'delete', component: DeleteAccountComponent }
    ],
  },
  { path: 'login', component: LoginComponent, canActivate: [SafeGuardService] },
  { path: 'register', component: RegisterComponent, canActivate: [SafeGuardService] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }