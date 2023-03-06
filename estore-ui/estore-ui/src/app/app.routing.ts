import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from '../home';
import { LoginComponent } from '../login';
import { RegisterComponent } from '../register';

const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const appRoutingModule = RouterModule.forRoot(routes);