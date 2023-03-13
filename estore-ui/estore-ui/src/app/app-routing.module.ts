import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuardService } from './services/auth-guard.service';
import { SafeGuardService } from './services/safe-guard.service';
import { AdminGuardService } from './services/admin-guard.service';

import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { HomeComponent } from './home/home.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { RegisterComponent } from './register/register.component';
import { MenuComponent } from './menu/menu.component';
import { AdminMenuComponent } from './admin-menu/admin-menu.component';
import { IngredientsComponent } from './ingredients/ingredients.component';

const routes: Routes = [
    { path: '', component: MenuComponent, children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent, canActivate: [AuthGuardService]}]},
    
    { path: 'admin', component: AdminMenuComponent, children: [
      { path: '', component: AdminHomeComponent, canActivate: [AdminGuardService]},
      { path: 'ingredients', component: IngredientsComponent, canActivate: [AdminGuardService]}]},

    { path: 'login', component: LoginComponent, canActivate: [SafeGuardService] },
    { path: 'register', component: RegisterComponent, canActivate: [SafeGuardService] },
    { path: 'logout', component: LogoutComponent }
];

@NgModule({
    imports: [ RouterModule.forRoot(routes) ],
    exports: [ RouterModule ]
  })
export class AppRoutingModule {}