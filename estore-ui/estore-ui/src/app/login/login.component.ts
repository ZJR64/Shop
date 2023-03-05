import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location
  ) {
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) { 
        this.router.navigate(['/']);
    }
}

  verify(email: string, password: string): void {
    users: User[];
    user: User;
    email = email.trim();
    password = password.trim();
    if (!email) { return; }
    if (!password) { return; }

    this.userService.getUsers().pipe(User.find(txn => txn.id === id);
  }

}
