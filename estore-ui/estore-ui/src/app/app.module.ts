import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { LogoutComponent } from './logout/logout.component';
import { MessagesComponent } from './messages/messages.component';

import { MenuComponent } from './menu/menu.component';
import { ProductsComponent } from './products/products.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductSearchComponent } from './product-search/product-search.component';
import { ProductIngredientsComponent } from './product-ingredients/product-ingredients.component';
import { IngredientsComponent } from './ingredients/ingredients.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminMenuComponent } from './admin-menu/admin-menu.component';
import { IngredientDetailsComponent } from './ingredient-details/ingredient-details.component';
import { UniversalMenuComponent } from './universal-menu/universal-menu.component';
import { CartComponent } from './cart/cart.component';
import { AdminsComponent } from './admins/admins.component';
import { UserSettingsComponent } from './user-settings/user-settings.component';
import { DeleteAccountComponent } from './delete-account/delete-account.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent,
    MessagesComponent,
    MenuComponent,
    AdminHomeComponent,
    AdminMenuComponent,
    IngredientsComponent,
    IngredientDetailsComponent,
    UniversalMenuComponent,
    AdminsComponent,
    ProductsComponent,
    ProductDetailComponent,
    MessagesComponent,
    DashboardComponent,
    ProductSearchComponent,
    ProductIngredientsComponent,
    CartComponent,
    UserSettingsComponent,
    DeleteAccountComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }